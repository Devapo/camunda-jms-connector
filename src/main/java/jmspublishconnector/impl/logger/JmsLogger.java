package jmspublishconnector.impl.logger;

import org.camunda.commons.logging.BaseLogger;

public class JmsLogger extends BaseLogger {

    public static final String PROJECT_CODE = "JMSCNTR";

    public static JmsPublishConnectorLogger JMS_LOGGER = createLogger(JmsPublishConnectorLogger.class, PROJECT_CODE, "123", "1");
}
