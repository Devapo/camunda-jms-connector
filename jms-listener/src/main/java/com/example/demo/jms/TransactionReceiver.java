package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${json.instanceid}")
    private String INSTANCE_ID;

    @Value("${json.payload}")
    private String PAYLOAD;

    public static boolean paramIsNotEmpty(String param){
        return (param != null && !param.isEmpty());
    }

    @JmsListener(destination = "test")
    public void receiveMessagesAndTriggerInstance(Message message) throws JMSException {

        String convertedMessage =((TextMessage) message).getText();

        LOGGER.info("Message successfully received. MSG: " + convertedMessage);

        JSONObject jsonObject = new JSONObject(convertedMessage);

        Object INSTANCE_ID = jsonObject.opt(this.INSTANCE_ID);
        Object PAYLOAD = jsonObject.opt(this.PAYLOAD);

        if(paramIsNotEmpty(INSTANCE_ID.toString()) && paramIsNotEmpty(PAYLOAD.toString()))
        {
            runtimeService.createMessageCorrelation(PAYLOAD.toString())
                    .setVariable("ID", INSTANCE_ID.toString())
                    .setVariable("PAYLOAD", PAYLOAD.toString())
                    .processInstanceBusinessKey(INSTANCE_ID.toString())
                    .correlate();
        } else {
            LOGGER.warn("EMPTY JSON PARAMETER");
        }
    }
}
//https://docs.camunda.org/javadoc/camunda-bpm-platform/7.3/org/camunda/bpm/engine/RuntimeService.html
