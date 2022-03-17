package com.example.demo.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;

public class TransactionReceiver {

    private final static Logger log = LoggerFactory.getLogger(TransactionReceiver.class);

    private final JmsMsgDeserializer jmsMsgDeserializer;
    private final CamundaProcessStarter camundaProcessStarter;

    public TransactionReceiver(JmsMsgDeserializer jmsMsgDeserializer, CamundaProcessStarter camundaProcessStarter) {
        this.jmsMsgDeserializer = jmsMsgDeserializer;
        this.camundaProcessStarter = camundaProcessStarter;
    }

    @JmsListener(destination = "test")
    public void receiveMessagesAndTriggerInstance(Message message) throws JMSException {
        DeserializedMessage deserializedMessage = jmsMsgDeserializer.deserialize(message);
        log.info("Message received: {}", deserializedMessage);

        if (isMessageValid(deserializedMessage)) {
            camundaProcessStarter.startProcessByMessage(deserializedMessage);
        } else {
            log.warn("Empty JSON parameter. Message will not be processed.");
        }
    }

    private boolean isMessageValid(DeserializedMessage deserializedMessage) {
        return isNotEmpty(deserializedMessage.getInstanceId()) && isNotEmpty(deserializedMessage.getPayload());
    }

    private static boolean isNotEmpty(String param) {
        return (param != null && !param.isEmpty());
    }
}
