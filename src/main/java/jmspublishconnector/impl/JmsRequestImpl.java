package jmspublishconnector.impl;

import jmspublishconnector.JmsPublishConnector;
import jmspublishconnector.JmsRequest;
import jmspublishconnector.JmsResponse;
import org.camunda.connect.impl.AbstractConnectorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class JmsRequestImpl extends AbstractConnectorRequest<JmsResponse> implements JmsRequest {

    private final static Logger LOGGER = LoggerFactory.getLogger(JmsRequest.class);

    public JmsRequestImpl(JmsPublishConnector connector){
        super(connector);
    }

    @Override
    protected boolean isRequestValid(){

        List<String> inputParams = Arrays.asList(
                getRequestParameter(JmsRequest.PARAM_NAME_MESSAGE),
                getRequestParameter(JmsRequest.PARAM_NAME_QUEUE),
                getRequestParameter(JmsRequest.PARAM_NAME_URL));

        inputParams.forEach(param -> {
            if(param == null || param.isBlank()){
                LOGGER.warn("Empty input parameter!");
            }
        });

        return true;
    }
}
