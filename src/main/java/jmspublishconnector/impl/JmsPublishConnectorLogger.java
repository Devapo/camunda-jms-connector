package jmspublishconnector.impl;

import org.camunda.connect.ConnectorRequestException;
import org.camunda.connect.impl.ConnectCoreLogger;

public class JmsPublishConnectorLogger extends ConnectCoreLogger {
    public ConnectorRequestException jmsUrlRequired(){
        return new ConnectorRequestException(exceptionMessage("001", "JMS url is required."));
    }
}
