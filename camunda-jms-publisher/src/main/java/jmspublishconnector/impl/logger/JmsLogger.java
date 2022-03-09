package jmspublishconnector.impl.logger;

import org.camunda.commons.logging.BaseLogger;

public class JmsLogger extends BaseLogger {

    public static JmsPublishConnectorLogger JMS_LOGGER = createLogger(
            JmsPublishConnectorLogger.class,
            "JMSCNTR",
            "JMS_LOGGER",
            "01"
    );

}
