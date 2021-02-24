package jmspublishconnector.impl;
import static org.assertj.core.api.Assertions.assertThat;

import jmspublishconnector.JmsPublishConnector;
import jmspublishconnector.JmsRequest;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.junit.EmbeddedJMSResource;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.camunda.connect.Connectors;
import org.camunda.connect.impl.DebugRequestInterceptor;
import org.camunda.connect.spi.Connector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import javax.jms.*;

public class JmsPublishConnectorTest {

    @Rule
    public EmbeddedJMSResource broker = new EmbeddedJMSResource();

    protected JmsPublishConnector connector;
    protected DebugRequestInterceptor interceptor;

    @Before
    public void createConnector(){
        connector = new JmsPublishConnectorImpl();
        interceptor = new DebugRequestInterceptor(false);
        connector.addRequestInterceptor(interceptor);
    }

    @Test
    public void shouldDiscoverConnector(){
        Connector jmsPublisher = Connectors.getConnector(JmsPublishConnector.ID);
        assertThat(jmsPublisher).isNotNull();
    }

    @Test
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
    }

}