package jmspublishconnector.impl;

import jmspublishconnector.JmsPublishConnector;
import jmspublishconnector.JmsRequest;
import jmspublishconnector.JmsResponse;
import org.camunda.connect.impl.AbstractConnector;
import org.camunda.connect.spi.ConnectorResponse;

public class JmsPublishConnectorImpl extends AbstractConnector<JmsRequest, JmsResponse> implements JmsPublishConnector {

    public JmsPublishConnectorImpl() {
        super(JmsPublishConnector.ID);
    }

    public JmsPublishConnectorImpl(String connectorId) {
        super(connectorId);
    }

    public JmsRequest createRequest() {
        return null;
    }

    public ConnectorResponse execute(JmsRequest jmsRequest) {
        return null;
    }
}
