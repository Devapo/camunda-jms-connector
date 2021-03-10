package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
@EnableJms
public class TransactionReceiver {
    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionReceiver.class);
    @Autowired
    RuntimeService runtimeService;

    @JmsListener(destination = "test")
    public void receiveMessages(Message message) throws JMSException {
        String convertedMessage =((TextMessage) message).getText();
        System.out.println("Received <" + convertedMessage + ">");

        JSONObject jsonObject = new JSONObject(convertedMessage);

        Object PROCESS_INSTANCE_BUSINESS_KEY = jsonObject.opt("bkey");
        Object NEW_PROCESS_BUSINESS_KEY = jsonObject.opt("new_bkey");
        Object MESSAGE = jsonObject.opt("msg");

        /* IF INSTANCE ID IS KNOWN TRIGGER A MSG IN ACTIVE INSTANCE
           ELSE TRIGGER A MESSAGE IN AN ENGINE TO START A NEW INSTANCE
                               WITH A GIVEN MSG AS A BUSINESS KEY */

        if(MESSAGE != null && !MESSAGE.toString().isEmpty()){
            if(PROCESS_INSTANCE_BUSINESS_KEY != null && !PROCESS_INSTANCE_BUSINESS_KEY.toString().isEmpty()){
                runtimeService.createMessageCorrelation(MESSAGE.toString())
                        .processInstanceBusinessKey(PROCESS_INSTANCE_BUSINESS_KEY.toString())
                        .correlate();
            } else if(NEW_PROCESS_BUSINESS_KEY != null && !NEW_PROCESS_BUSINESS_KEY.toString().isEmpty()){
                runtimeService.startProcessInstanceByMessage(MESSAGE.toString(), NEW_PROCESS_BUSINESS_KEY.toString());
            } else {
                LOGGER.warn("BUSINESS KEY IS EMPTY!");
                //System.out.println();
            }
        } else {
            LOGGER.warn("MESSAGE IS EMPTY");
        }
    }
}

//https://docs.camunda.org/javadoc/camunda-bpm-platform/7.3/org/camunda/bpm/engine/RuntimeService.html