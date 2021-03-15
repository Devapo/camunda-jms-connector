package jmspublishconnector.impl.util;

import jmspublishconnector.JmsRequest;

import static jmspublishconnector.impl.util.RequestExceptionHandler.requestException;

public class EmptyParamValidator {
    public static boolean paramIsEmpty(String param, JmsRequest jmsRequest){
        return (jmsRequest.getRequestParameter(param) == null ||
                jmsRequest.getRequestParameter(param).toString().isEmpty());
    }

    public static boolean validateParams(JmsRequest jmsRequest){
        boolean ifValid = true;

        if(paramIsEmpty(JmsRequest.PARAM_NAME_MESSAGE, jmsRequest)){
            ifValid = false;
            requestException(JmsRequest.PARAM_NAME_MESSAGE);
        } else if(paramIsEmpty(JmsRequest.PARAM_NAME_QUEUE, jmsRequest)){
            ifValid = false;
            requestException(JmsRequest.PARAM_NAME_QUEUE);
        } else if(paramIsEmpty(JmsRequest.PARAM_NAME_URL, jmsRequest)){
            ifValid = false;
            requestException(JmsRequest.PARAM_NAME_URL);
        }

        return ifValid;
    }
}
