package jmsconnector;

import org.camunda.connect.spi.Connector;

public interface JmsConnector  extends Connector<JmsRequest> {
    public static final String ID = "jms-connector";
}
