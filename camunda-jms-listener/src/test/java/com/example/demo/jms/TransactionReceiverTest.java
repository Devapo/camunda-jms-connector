package com.example.demo.jms;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.jms.JMSException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TransactionReceiverTest {

    BrokerService broker;

//    protected JmsPublishConnector connector;
//    protected DebugRequestInterceptor interceptor;

    @BeforeEach
    void setUp() throws Exception {
//        connector = new JmsPublishConnectorImpl();
//        interceptor = new DebugRequestInterceptor(false);
//        connector.addRequestInterceptor(interceptor);

        broker = new BrokerService();
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI("tcp://localhost:61610"));
        broker.addConnector(connector);
        broker.setDeleteAllMessagesOnStartup(true);
        broker.start();

    }

    public QueueViewMBean getProxyToQueue(String name) throws MalformedObjectNameException {
        ObjectName queueViewMBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=" + name);
        return  (QueueViewMBean) broker
                .getManagementContext()
                .newProxyInstance(queueViewMBeanName, QueueViewMBean.class, true);
    }

    @Test
    public void shouldDiscoverConnector(){
       /* Connector jmsPublisher = Connectors.getConnector(JmsPublishConnector.ID);

        assertThat(jmsPublisher).isNotNull();

        assertThat(jmsPublisher.getId())
                .isEqualTo(JmsPublishConnector.ID);*/
    }

   /* @Test
    public void connectorSendsMessageToJms() throws IOException, MalformedObjectNameException, JMSException {

        JmsRequest request = new JmsRequestImpl(connector);

        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "test1");
        request.setRequestParameter("message", "{'bkey':'business_key','msg':'payload_data'}");

        connector.execute(request);
        broker.
        broker.checkQueueSize("test1");

        QueueViewMBean queueViewMBean = getProxyToQueue("test1");

        assertThat(queueViewMBean.getQueueSize()).isEqualTo(1);

        connector.execute(request);

        assertThat(queueViewMBean.getQueueSize()).isEqualTo(2);
    }*/

   /* @Test(expected = ConnectorRequestException.class)
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
    }*/

    @After
    public void tearDownAMQ() throws Exception {
        broker.stop();
    }

}