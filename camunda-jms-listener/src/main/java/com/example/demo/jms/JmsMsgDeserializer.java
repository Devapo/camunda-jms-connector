package com.example.demo.jms;

import org.json.JSONObject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

public class JmsMsgDeserializer {

    private final String instanceId;
    private final String payload;

    public JmsMsgDeserializer(String instanceId, String payload) {
        this.instanceId = instanceId;
        this.payload = payload;
    }

    public DeserializedMessage deserialize(Message message) throws JMSException {
        String convertedMessage = ((TextMessage) message).getText();

        JSONObject jsonObject = new JSONObject(convertedMessage);

        Object instanceId = jsonObject.opt(this.instanceId);
        Object payload = jsonObject.opt(this.payload);

        return new DeserializedMessage(
                instanceId != null ? instanceId.toString() : null,
                payload != null ? payload.toString() : null
        );
    }
}
