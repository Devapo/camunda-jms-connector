package com.example.demo.jms;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@RequiredArgsConstructor
public class JmsMsgDeserializer {

    private final String INSTANCE_ID;
    private final String PAYLOAD;

    public DeserializedMessage deserialize(Message message) throws JMSException {

        String convertedMessage =((TextMessage) message).getText();

        JSONObject jsonObject = new JSONObject(convertedMessage);

        Object INSTANCE_ID = jsonObject.opt(this.INSTANCE_ID);
        Object PAYLOAD = jsonObject.opt(this.PAYLOAD);
        return new DeserializedMessage(INSTANCE_ID.toString(), PAYLOAD.toString());
    }
}
