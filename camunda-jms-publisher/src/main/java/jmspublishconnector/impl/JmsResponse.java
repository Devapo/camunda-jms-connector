package jmspublishconnector.impl;

import org.camunda.connect.impl.AbstractConnectorResponse;
import org.camunda.connect.spi.CloseableConnectorResponse;

import java.io.Closeable;
import java.util.Map;

public class JmsResponse extends AbstractConnectorResponse implements CloseableConnectorResponse, Closeable {

    public JmsResponse() {
    }

    public void close() {
    }

    @Override
    protected void collectResponseParameters(Map<String, Object> map) {
    }
}
