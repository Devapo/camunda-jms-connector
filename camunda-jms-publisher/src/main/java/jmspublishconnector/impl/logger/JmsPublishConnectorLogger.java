package jmspublishconnector.impl.logger;

import org.camunda.connect.ConnectorRequestException;
import org.camunda.connect.impl.ConnectLogger;

public class JmsPublishConnectorLogger extends ConnectLogger {

    public ConnectorRequestException jmsUrlRequired() {
        return new ConnectorRequestException(exceptionMessage("001", "JMS url is required."));
    }

    public ConnectorRequestException jmsQueueRequired() {
        return new ConnectorRequestException(exceptionMessage("002", "JMS queue name is required"));
    }

    public ConnectorRequestException jmsMessageRequired() {
        return new ConnectorRequestException(exceptionMessage("003", "JMS message is required"));
    }

    public ConnectorRequestException jmsWrongJsonMessageFormat() {
        return new ConnectorRequestException(exceptionMessage("004", "Wrong message JSON format! Should contain two keys."));
    }
}
