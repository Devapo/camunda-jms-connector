package jmspublishconnector.impl;

import jmspublishconnector.JmsPublishConnector;
import jmspublishconnector.JmsRequest;
import jmspublishconnector.JmsResponse;
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
        // Default ActiveMQ can be acquired by "ActiveMQConnection.DEFAULT_BROKER_URL"
        // and it's value is "failover://tcp://localhost:61616".
        String PARAM_NAME_URL = jmsRequest.getRequestParameter(JmsRequest.PARAM_NAME_URL);
        String PARAM_NAME_QUEUE = jmsRequest.getRequestParameter(JmsRequest.PARAM_NAME_QUEUE);
        String PARAM_NAME_MESSAGE = jmsRequest.getRequestParameter(JmsRequest.PARAM_NAME_MESSAGE);

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(PARAM_NAME_URL);
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(PARAM_NAME_QUEUE);

            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage(PARAM_NAME_MESSAGE);

            producer.send(message);

            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return new JmsResponseImpl();
    }
}
