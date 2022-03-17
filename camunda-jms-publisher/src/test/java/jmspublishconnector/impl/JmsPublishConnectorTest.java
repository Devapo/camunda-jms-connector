package jmspublishconnector.impl;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.camunda.connect.ConnectorRequestException;
import org.camunda.connect.Connectors;
import org.camunda.connect.impl.DebugRequestInterceptor;
import org.camunda.connect.spi.Connector;


import javax.jms.JMSException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.IOException;
import java.net.URI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JmsPublishConnectorTest {

    private static BrokerService broker;
    private static JmsPublishConnector connector;
    private static DebugRequestInterceptor interceptor;

    @BeforeAll
    static void createConnector() {
        connector = new JmsPublishConnector();
        interceptor = new DebugRequestInterceptor(false);
        connector.addRequestInterceptor(interceptor);
    }

    @BeforeEach
    void setupAMQ() throws Exception {
        broker = new BrokerService();
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI("tcp://localhost:61610"));
        broker.addConnector(connector);
        broker.setDeleteAllMessagesOnStartup(true);
        broker.start();
    }

    QueueViewMBean getProxyToQueue(String name) throws MalformedObjectNameException, JMSException {
        ObjectName queueViewMBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=" + name);
        QueueViewMBean proxy = (QueueViewMBean) broker.getManagementContext()
                .newProxyInstance(queueViewMBeanName, QueueViewMBean.class, true);
        return proxy;
    }

    @Test
    void shouldDiscoverConnector() {
        Connector jmsPublisher = Connectors.getConnector(JmsPublishConnector.ID);

        assertThat(jmsPublisher).isNotNull();

        assertThat(jmsPublisher.getId())
                .isEqualTo(JmsPublishConnector.ID);
    }

    @Test
    void connectorSendsMessageToJms() throws IOException, MalformedObjectNameException, JMSException {

        JmsRequest request = new JmsRequest(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "test1");
        request.setRequestParameter("message", "{'bkey':'business_key','msg':'payload_data'}");

        connector.execute(request);
        broker.checkQueueSize("test1");

        QueueViewMBean queueViewMBean = getProxyToQueue("test1");

        assertThat(queueViewMBean.getQueueSize()).isEqualTo(1);

        connector.execute(request);

        assertThat(queueViewMBean.getQueueSize()).isEqualTo(2);
    }

    @Test
    void exceptionIsThrownUponEmptyMessage() {
        JmsRequest request = new JmsRequest(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "test1");
        request.setRequestParameter("message", "");

        assertThrows(ConnectorRequestException.class, () -> connector.execute(request));
    }

    @Test
    void exceptionIsThrownUponEmptyQueue() {
        JmsRequest request = new JmsRequest(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "");
        request.setRequestParameter("message", "test");

        assertThrows(ConnectorRequestException.class, () -> connector.execute(request));
    }

    @Test
    void exceptionIsThrownUponEmptyUrl() {
        JmsRequest request = new JmsRequest(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "test1");
        request.setRequestParameter("message", "");

        assertThrows(ConnectorRequestException.class, () -> connector.execute(request));
    }

    @Test
    void exceptionIsThrownUponJsonWithWrongKeys() {
        JmsRequest request = new JmsRequest(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "test1");
        request.setRequestParameter("message", "{'wrong':'json', 'keys':'test', 'hello':'world'}");

        assertThrows(ConnectorRequestException.class, () -> connector.execute(request));
    }

    @AfterEach
    public void tearDownAMQ() throws Exception {
        broker.stop();
    }
}
