package jmspublishconnector;

import org.camunda.connect.spi.ConnectorRequest;

public interface JmsRequest extends ConnectorRequest<JmsResponse> {
    public static String PARAM_NAME_MESSAGE = "message";
    public static String PARAM_NAME_URL = "url";
    public static String PARAM_NAME_QUEUE = "queue";
}
