package com.example.demo.jms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

class JmsMsgDeserializerTest {

    @Test
    void jmsMsgDeserializerTest() throws JMSException {
        JmsMsgDeserializer jmsMsgDeserializer = new JmsMsgDeserializer("id", "payload");
        DeserializedMessage deserializedMessage = new DeserializedMessage("id", "payload");

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("{'id':'id','payload':'payload'}");

        assertThat(jmsMsgDeserializer.deserialize(message)).hasToString(deserializedMessage.toString());
    }

    @Test
    void jmsMsgDeserializerReturnNullInstanceIdWhenIncorrectlyFormatted() throws JMSException {
        JmsMsgDeserializer jmsMsgDeserializer = new JmsMsgDeserializer("id", "payload");
        DeserializedMessage deserializedMessage = new DeserializedMessage(null, "payload");

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("{'':'','payload':'payload'}");

        assertThat(jmsMsgDeserializer.deserialize(message)).hasToString(deserializedMessage.toString());
    }

    @Test
    void jmsMsgDeserializerThrowsExceptionUponWrongParam() throws JMSException {
        JmsMsgDeserializer jmsMsgDeserializer = new JmsMsgDeserializer("id", "payload");
        DeserializedMessage deserializedMessage = new DeserializedMessage("wrong", "payload");

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("{'wrong':'id','params':'payload'}");

        assertThat(jmsMsgDeserializer.deserialize(message)).hasToString(deserializedMessage.toString());
    }

    @Test
    void jmsMsgDeserializerThrowsExceptionUponWrongJsonFormat() throws JMSException {
        JmsMsgDeserializer jmsMsgDeserializer = new JmsMsgDeserializer("id", "payload");

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("'wrong':'json','format':'payload'}");

        assertThrows(JSONException.class, () -> jmsMsgDeserializer.deserialize(message));
    }
}