package jmsconnector;

import org.camunda.connect.spi.CloseableConnectorResponse;

import java.io.Closeable;

public interface JmsResponse extends CloseableConnectorResponse, Closeable {
}
