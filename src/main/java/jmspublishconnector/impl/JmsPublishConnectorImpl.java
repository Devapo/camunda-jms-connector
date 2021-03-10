package jmspublishconnector.impl;

import jmspublishconnector.JmsPublishConnector;
import jmspublishconnector.JmsRequest;
import jmspublishconnector.JmsResponse;
import jmspublishconnector.impl.logger.JmsLogger;
import jmspublishconnector.impl.logger.JmsPublishConnectorLogger;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.camunda.connect.impl.AbstractConnector;
import org.camunda.connect.spi.ConnectorResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import java.io.Serializable;

import static jmspublishconnector.impl.util.EmptyParamValidator.validateParams;
import static jmspublishconnector.impl.util.JsonValidator.isJSONValid;

public class JmsPublishConnectorImpl extends AbstractConnector<JmsRequest, JmsResponse> implements JmsPublishConnector {

    protected static JmsPublishConnectorLogger LOG = JmsLogger.JMS_LOGGER;
    private final static Logger LOGGER = LoggerFactory.getLogger(JmsPublishConnectorImpl.class);

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

        if(validateParams(jmsRequest) && isJSONValid(jmsRequest.getRequestParameter(JmsRequest.PARAM_NAME_MESSAGE))){
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

                LOGGER.info("Message successfully sent. URL: {} QUEUE: {} MSG: {}",
                        PARAM_NAME_URL, PARAM_NAME_QUEUE, PARAM_NAME_MESSAGE);

                connection.close();
            } catch (JMSException e) {
                LOGGER.warn("Could not sent a message. URL: {} QUEUE: {} MSG: {}",
                        PARAM_NAME_URL, PARAM_NAME_QUEUE, PARAM_NAME_MESSAGE);
                e.printStackTrace();
            }
        }

        return new JmsResponseImpl();
    }
}
