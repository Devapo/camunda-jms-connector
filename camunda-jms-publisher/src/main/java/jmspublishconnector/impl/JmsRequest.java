package jmspublishconnector.impl;

import org.camunda.connect.impl.AbstractConnectorRequest;
import org.camunda.connect.spi.ConnectorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class JmsRequest extends AbstractConnectorRequest<JmsResponse> implements ConnectorRequest<JmsResponse> {

    private static final Logger log = LoggerFactory.getLogger(JmsRequest.class);

    public JmsRequest(JmsPublishConnector connector) {
        super(connector);
    }

    public String getUrl() {
        return getRequestParameter("url");
    }

    public String getQueue() {
        return getRequestParameter("queue");
    }

    public String getMessage() {
        return getRequestParameter("message");
    }

    @Override
    protected boolean isRequestValid() {
        List<String> inputParams = Arrays.asList(
                getMessage(),
                getQueue(),
                getUrl()
        );

        if (containsEmptyParams(inputParams)) {
            log.warn("Empty input parameter!");
        }

        return true;
    }

    private boolean containsEmptyParams(List<String> inputParams) {
        return inputParams.stream()
                .anyMatch(param -> param == null || param.isBlank());
    }
}
