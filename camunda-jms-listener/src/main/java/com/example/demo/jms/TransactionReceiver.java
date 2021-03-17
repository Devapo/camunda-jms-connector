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

    @Autowired
    private JmsMsgDeserializer myDeserializer;

    @Autowired
    private CamundaProcessStarter camundaProcessStarter;

    public static boolean paramIsNotEmpty(String param){
        return (param != null && !param.isEmpty());
    }

    @JmsListener(destination = "test")
    public void receiveMessagesAndTriggerInstance(Message message) throws JMSException {

        DeserializedMessage msg = myDeserializer.deserialize(message);
        LOGGER.info("Message successfully received. MSG: " + msg.toString());

        if(paramIsNotEmpty(msg.getInstanceId()) && paramIsNotEmpty(msg.getPayload()))
        {
            camundaProcessStarter.startProcessByMessage(msg);
        } else {
            LOGGER.warn("EMPTY JSON PARAMETER");
        }
    }
}
//https://docs.camunda.org/javadoc/camunda-bpm-platform/7.3/org/camunda/bpm/engine/RuntimeService.html
