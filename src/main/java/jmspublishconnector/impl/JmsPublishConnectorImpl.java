package jmspublishconnector.impl;

import jmspublishconnector.JmsPublishConnector;
import jmspublishconnector.JmsRequest;
import jmspublishconnector.JmsResponse;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.camunda.connect.impl.AbstractConnector;
import org.camunda.connect.spi.ConnectorResponse;

import javax.jms.*;

public class JmsPublishConnectorImpl extends AbstractConnector<JmsRequest, JmsResponse> implements JmsPublishConnector {

    public JmsPublishConnectorImpl() {
        super(JmsPublishConnector.ID);
    }

    public JmsPublishConnectorImpl(String connectorId) {
        super(connectorId);
    }

    public JmsRequest createRequest() {
        return new JmsRequestImpl(this);
    }

    public ConnectorResponse execute(JmsRequest jmsRequest) {
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        String queueName = "test";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(queueName);

            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage("Hi Peter, How are you?");

            producer.send(message);

            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return new JmsResponseImpl();
    }
}
