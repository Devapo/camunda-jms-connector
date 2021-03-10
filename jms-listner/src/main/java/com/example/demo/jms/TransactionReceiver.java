package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;
import org.json.JSONObject;
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
    @Autowired
    RuntimeService runtimeService;

    @JmsListener(destination = "test")
    public void receiveMessages(Message message) throws JMSException {
        String convertedMessage =((TextMessage) message).getText();
        System.out.println("Received <" + convertedMessage + ">");

        JSONObject jsonObject = new JSONObject(convertedMessage);

        Object BUSINESS_KEY = jsonObject.opt("bkey");
        Object MESSAGE = jsonObject.opt("msg");
        Object NEW_BUSINESS_KEY = jsonObject.opt("new_bkey");

        /* IF INSTANCE ID IS KNOWN TRIGGER A MSG IN ACTIVE INSTANCE
           ELSE TRIGGER A MESSAGE IN AN ENGINE TO START A NEW INSTANCE
                               WITH A GIVEN MSG AS A BUSINESS KEY */
        if(MESSAGE != null && !MESSAGE.toString().isEmpty()){
            if(BUSINESS_KEY != null && !BUSINESS_KEY.toString().isEmpty()){
                runtimeService.createMessageCorrelation(MESSAGE.toString())
                        .processInstanceBusinessKey(BUSINESS_KEY.toString())
                        .correlate();
            } else {
                System.out.println("BUSINESS KEY EMPTY");
                System.out.println(NEW_BUSINESS_KEY.toString());
                runtimeService.startProcessInstanceByMessage(MESSAGE.toString(), NEW_BUSINESS_KEY.toString());
            }
        } else {
            System.out.println("MESSAGE IS EMPTY");
        }
    }
}

//https://docs.camunda.org/javadoc/camunda-bpm-platform/7.3/org/camunda/bpm/engine/RuntimeService.html