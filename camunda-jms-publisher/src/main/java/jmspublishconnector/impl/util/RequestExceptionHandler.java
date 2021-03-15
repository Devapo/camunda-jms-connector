package jmspublishconnector.impl.util;

import jmspublishconnector.JmsRequest;
import jmspublishconnector.impl.logger.JmsLogger;
import jmspublishconnector.impl.logger.JmsPublishConnectorLogger;

public class RequestExceptionHandler {

    protected static JmsPublishConnectorLogger LOG = JmsLogger.JMS_LOGGER;

    public static void requestException(String param){
        switch (param)
        {
            case JmsRequest.PARAM_NAME_MESSAGE:
                throw LOG.jmsMessageRequired();

            case JmsRequest.PARAM_NAME_QUEUE:
                throw LOG.jmsQueueRequired();

            case JmsRequest.PARAM_NAME_URL:
                throw LOG.jmsUrlRequired();

            case "JSON_ERROR":
                throw LOG.jmsWrongJsonMessageFormat();
        }
    }
}
