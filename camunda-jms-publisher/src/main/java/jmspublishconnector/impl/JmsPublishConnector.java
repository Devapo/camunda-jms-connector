package jmspublishconnector.impl;

import jmspublishconnector.impl.logger.JmsLogger;
import jmspublishconnector.impl.logger.JmsPublishConnectorLogger;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.camunda.connect.impl.AbstractConnector;
import org.camunda.connect.spi.Connector;
import org.camunda.connect.spi.ConnectorResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.lang.IllegalStateException;

public class JmsPublishConnector extends AbstractConnector<JmsRequest, JmsResponse> implements Connector<JmsRequest> {

    private static final Logger log = LoggerFactory.getLogger(JmsPublishConnector.class);
    private static final JmsPublishConnectorLogger jmsLog = JmsLogger.JMS_LOGGER;
    public static final String ID = "jms-publish-connector";

    public JmsPublishConnector() {
        super(JmsPublishConnector.ID);
    }

    public JmsPublishConnector(String connectorId) {
        super(connectorId);
    }

    @Override
    public JmsRequest createRequest() {
        return new JmsRequest(this);
    }

    @Override
    public ConnectorResponse execute(JmsRequest jmsRequest) {
        validateRequest(jmsRequest);

        // Default ActiveMQ can be acquired by "ActiveMQConnection.DEFAULT_BROKER_URL"
        // and it's value is "failover://tcp://localhost:61616".
        String url = jmsRequest.getUrl();
        String queue = jmsRequest.getQueue();
        String message = jmsRequest.getMessage();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queue);
            MessageProducer producer = session.createProducer(destination);

            log.info("Sending message. URL: {} QUEUE: {} MSG: {}", url, queue, message);
            producer.send(session.createTextMessage(message));
        } catch (JMSException e) {
            log.warn("Could not sent a message", e);
        } finally {
            if (connection != null) {
                closeConnection(connection);
            }
        }

        return new JmsResponse();
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (JMSException e) {
            throw new IllegalStateException("Could not close connection", e);
        }
    }

    private void validateRequest(JmsRequest jmsRequest) {
        if (isEmpty(jmsRequest.getMessage())) {
            throw jmsLog.jmsMessageRequired();
        }

        if (isEmpty(jmsRequest.getQueue())) {
            throw jmsLog.jmsQueueRequired();
        }

        if (isEmpty(jmsRequest.getUrl())) {
            throw jmsLog.jmsUrlRequired();
        }

        validateJson(jmsRequest.getMessage());
    }

    private static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private void validateJson(String test) {
        try {
            JSONObject jsonObject = new JSONObject(test);
            // Check if input json contains all necessary keys
            if (jsonObject.length() != 2) {
                throw jmsLog.jmsWrongJsonMessageFormat();
            }
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                throw jmsLog.jmsWrongJsonMessageFormat();
            }
        }
    }

}
