package com.example.demo.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
@EnableJms
public class TransactionReceiver {
    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionReceiver.class);

    @Autowired
    private JmsMsgDeserializer jmsMsgDeserializer;

    @Autowired
    private CamundaProcessStarter camundaProcessStarter;

    public static boolean paramIsNotEmpty(String param){
        return (param != null && !param.isEmpty());
    }

    @JmsListener(destination = "test")
    public void receiveMessagesAndTriggerInstance(Message message) throws JMSException {
        DeserializedMessage deserializedMessage = jmsMsgDeserializer.deserialize(message);
        LOGGER.info("Message successfully received. MSG: " + deserializedMessage);

        if(paramIsNotEmpty(deserializedMessage.getInstanceId()) && paramIsNotEmpty(deserializedMessage.getPayload()))
        {
            camundaProcessStarter.startProcessByMessage(deserializedMessage);
        } else {
            LOGGER.warn("EMPTY JSON PARAMETER");
        }
    }
}
