package jmspublishconnector.impl;

import org.camunda.connect.ConnectorRequestException;
import org.camunda.connect.impl.ConnectLogger;

public class JmsPublishConnectorLogger extends ConnectLogger {
    public ConnectorRequestException jmsUrlRequired(){
        return new ConnectorRequestException(exceptionMessage("001", "JMS url is required."));
    }
}
