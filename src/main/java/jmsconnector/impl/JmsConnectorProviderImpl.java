package jmsconnector.impl;

import jmsconnector.JmsConnector;
import org.camunda.connect.spi.ConnectorProvider;

public class JmsConnectorProviderImpl implements ConnectorProvider {
    public String getConnectorId() {
        return JmsConnector.ID;
    }

    public JmsConnector createConnectorInstance() {
        return new JmsConnectorImpl(JmsConnector.ID);
    }
}
