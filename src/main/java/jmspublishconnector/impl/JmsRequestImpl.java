package jmspublishconnector.impl;

import jmspublishconnector.JmsPublishConnector;
import jmspublishconnector.JmsRequest;
import jmspublishconnector.JmsResponse;
import org.camunda.connect.impl.AbstractConnectorRequest;

public class JmsRequestImpl extends AbstractConnectorRequest<JmsResponse> implements JmsRequest {

    public JmsRequestImpl(JmsPublishConnector connector){
        super(connector);
    }
}
