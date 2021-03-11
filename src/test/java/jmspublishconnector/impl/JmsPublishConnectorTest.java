package jmspublishconnector.impl;

import jmspublishconnector.JmsPublishConnector;
import jmspublishconnector.JmsRequest;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.camunda.connect.ConnectorRequestException;
import org.camunda.connect.Connectors;
import org.camunda.connect.impl.DebugRequestInterceptor;
import org.camunda.connect.spi.Connector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jms.JMSException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class JmsPublishConnectorTest {

    BrokerService broker;

    protected JmsPublishConnector connector;
    protected DebugRequestInterceptor interceptor;

    @Before
    public void createConnector(){
        connector = new JmsPublishConnectorImpl();
        interceptor = new DebugRequestInterceptor(false);
        connector.addRequestInterceptor(interceptor);
    }

    @Before
    public void setupAMQ() throws Exception{
        broker = new BrokerService();
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI("tcp://localhost:61610"));
        broker.addConnector(connector);
        broker.setDeleteAllMessagesOnStartup(true);
        broker.start();
    }

    public QueueViewMBean getProxyToQueue(String name) throws MalformedObjectNameException, JMSException{
        ObjectName queueViewMBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName="+name);
        QueueViewMBean proxy = (QueueViewMBean) broker.getManagementContext()
                .newProxyInstance(queueViewMBeanName, QueueViewMBean.class, true);
        return proxy;
    }

    @Test
    public void shouldDiscoverConnector(){
        Connector jmsPublisher = Connectors.getConnector(JmsPublishConnector.ID);

        assertThat(jmsPublisher).isNotNull();

        assertThat(jmsPublisher.getId())
                .isEqualTo(JmsPublishConnector.ID);
    }

    @Test
    public void connectorSendsMessageToJms() throws IOException, MalformedObjectNameException, JMSException {

        JmsRequest request = new JmsRequestImpl(connector);

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

    @Test(expected = ConnectorRequestException.class)
    public void exceptionIsThrownUponEmptyMessage(){
        JmsRequest request = new JmsRequestImpl(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "test1");
        request.setRequestParameter("message", "");

        connector.execute(request);
    }

    @Test(expected = ConnectorRequestException.class)
    public void exceptionIsThrownUponEmptyQueue(){
        JmsRequest request = new JmsRequestImpl(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "");
        request.setRequestParameter("message", "test");

        connector.execute(request);
    }

    @Test(expected = ConnectorRequestException.class)
    public void exceptionIsThrownUponEmptyUrl(){
        JmsRequest request = new JmsRequestImpl(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "test1");
        request.setRequestParameter("message", "");

        connector.execute(request);
    }

    @Test(expected = ConnectorRequestException.class)
    public void exceptionIsThrownUponJsonWithWrongKeys(){
        JmsRequest request = new JmsRequestImpl(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "test1");
        request.setRequestParameter("message", "{'wrong':'json', 'keys':'test', 'hello':'world'}");

        connector.execute(request);
    }

    @After
    public void tearDownAMQ() throws Exception {
        broker.stop();
    }
}
