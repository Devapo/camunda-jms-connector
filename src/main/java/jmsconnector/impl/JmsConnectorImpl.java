package jmsconnector.impl;

import jmsconnector.JmsConnector;
import jmsconnector.JmsRequest;
import jmsconnector.JmsResponse;
import org.camunda.connect.impl.AbstractConnector;
import org.camunda.connect.spi.ConnectorResponse;

public class JmsConnectorImpl extends AbstractConnector<JmsRequest, JmsResponse> implements JmsConnector {

    public JmsConnectorImpl() {
        super(JmsConnector.ID);
    }

    public JmsConnectorImpl(String connectorId) {
        super(connectorId);
    }

    public JmsRequest createRequest() {
        return null;
    }

    public ConnectorResponse execute(JmsRequest jmsRequest) {
        return null;
    }
}
