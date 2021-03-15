package jmspublishconnector.impl.logger;

import org.camunda.commons.logging.BaseLogger;

public class JmsLogger extends BaseLogger {
    private static final String LOGGER_NAME = "JMS_LOGGER";
    private static final String COMPONENT_ID = "01";
    public static final String PROJECT_CODE = "JMSCNTR";

    public static JmsPublishConnectorLogger JMS_LOGGER = createLogger(JmsPublishConnectorLogger.class, PROJECT_CODE, LOGGER_NAME, COMPONENT_ID);
}
