package jmspublishconnector.impl;

import jmspublishconnector.JmsPublishConnector;
import org.camunda.connect.spi.ConnectorProvider;

public class JmsPublishConnectorProviderImpl implements ConnectorProvider {
    public String getConnectorId() {
        return JmsPublishConnector.ID;
    }

    public JmsPublishConnector createConnectorInstance() {
        return new JmsPublishConnectorImpl(JmsPublishConnector.ID);
    }
}
