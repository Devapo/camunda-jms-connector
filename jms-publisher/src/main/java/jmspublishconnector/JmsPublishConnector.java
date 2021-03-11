package jmspublishconnector;

import org.camunda.connect.spi.Connector;

public interface JmsPublishConnector extends Connector<JmsRequest> {
    public static final String ID = "jms-publish-connector";
}
