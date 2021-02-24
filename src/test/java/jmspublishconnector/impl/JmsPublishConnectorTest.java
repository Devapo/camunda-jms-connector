package jmspublishconnector.impl;

import jmspublishconnector.JmsPublishConnector;
import jmspublishconnector.JmsRequest;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.jmx.QueueViewMBean;
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

    //@Rule
    //public EmbeddedJMSResource broker = new EmbeddedJMSResource();
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
    public void setupAMQ() throws Exception {

        broker = new BrokerService();
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI("tcp://localhost:61610"));
        broker.addConnector(connector);
        broker.setDeleteAllMessagesOnStartup(true);
        // More config here!
        broker.start();
    }
    public QueueViewMBean getProxyToQueue(String name) throws MalformedObjectNameException, JMSException {
        ObjectName queueViewMBeanName = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName="+name);
        QueueViewMBean proxy = (QueueViewMBean) broker.getManagementContext()
                .newProxyInstance(queueViewMBeanName, QueueViewMBean.class, true);
        return proxy;
    }
    @Test
    public void shouldDiscoverConnector(){
        Connector jmsPublisher = Connectors.getConnector(JmsPublishConnector.ID);
        assertThat(jmsPublisher).isNotNull();
    }

    @Test
    public void connectorSendsMessageToJms() throws IOException, MalformedObjectNameException, JMSException {
        JmsRequest request = new JmsRequestImpl(connector);
        request.setRequestParameter("url", "tcp://localhost:61610");
        request.setRequestParameter("queue", "test1");
        request.setRequestParameter("message", "test");

        connector.execute(request);
        broker.checkQueueSize("test1");
        QueueViewMBean queueViewMBean = getProxyToQueue("test1");
        System.out.println("123123123xxx" + queueViewMBean.getQueueSize());
    }

    @After
    public void tearDownAMQ() throws Exception {
        broker.stop();
    }

   /* @Test
    public void connectorSendsMessageToJMS() throws JMSException {
        final ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        final javax.jms.Connection connection = connectionFactory.createConnection();
        //connection.setClientID("12345");
        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Topic temporaryTopic = session.createTemporaryTopic();

        final MessageConsumer consumer1 = session.createConsumer(temporaryTopic);

        final MessageProducer producer = session.createProducer(temporaryTopic);
        producer.send(session.createTextMessage("Testmessage"));

        final TextMessage message = (TextMessage)consumer1.receiveNoWait();

        Assert.assertNotNull(message);
        Assert.assertEquals("Testmessage", message.getText());
    }*/
}