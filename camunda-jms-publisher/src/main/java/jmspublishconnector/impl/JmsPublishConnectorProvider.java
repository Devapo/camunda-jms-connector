package jmspublishconnector.impl;

import org.camunda.connect.spi.ConnectorProvider;

public class JmsPublishConnectorProvider implements ConnectorProvider {

    @Override
    public String getConnectorId() {
        return JmsPublishConnector.ID;
    }

    @Override
    public JmsPublishConnector createConnectorInstance() {
        return new JmsPublishConnector(JmsPublishConnector.ID);
    }

}
