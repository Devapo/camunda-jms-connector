package com.example.demo.jms;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.json.JSONException;
import org.junit.Test;

import javax.jms.JMSException;

import static org.assertj.core.api.Assertions.assertThat;

public class JmsMsgDeserializerTest {

    @Test
    public void jmsMsgDeserializerTest() throws JMSException {
        JmsMsgDeserializer jmsMsgDeserializer = new JmsMsgDeserializer("id", "payload");

        DeserializedMessage deserializedMessage = new DeserializedMessage("id", "payload");

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("{'id':'id','payload':'payload'}");

        assertThat(jmsMsgDeserializer.deserialize(message)).hasToString(deserializedMessage.toString());
    }

    @Test(expected = NullPointerException.class)
    public void jmsMsgDeserializerThrowsExceptionUponEmptyParam() throws JMSException {
        JmsMsgDeserializer jmsMsgDeserializer = new JmsMsgDeserializer("id", "payload");

        DeserializedMessage deserializedMessage = new DeserializedMessage("id", "payload");

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("{'':'','payload':'payload'}");

        assertThat(jmsMsgDeserializer.deserialize(message)).hasToString(deserializedMessage.toString());
    }

    @Test(expected = NullPointerException.class)
    public void jmsMsgDeserializerThrowsExceptionUponWrongParam() throws JMSException {
        JmsMsgDeserializer jmsMsgDeserializer = new JmsMsgDeserializer("id", "payload");

        DeserializedMessage deserializedMessage = new DeserializedMessage("id", "payload");

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("{'wrong':'id','params':'payload'}");

        assertThat(jmsMsgDeserializer.deserialize(message)).hasToString(deserializedMessage.toString());
    }

    @Test(expected = JSONException.class)
    public void jmsMsgDeserializerThrowsExceptionUponWrongJsonFormat() throws JMSException {
        JmsMsgDeserializer jmsMsgDeserializer = new JmsMsgDeserializer("id", "payload");

        DeserializedMessage deserializedMessage = new DeserializedMessage("id", "payload");

        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("'wrong':'json','format':'payload'}");

        assertThat(jmsMsgDeserializer.deserialize(message)).hasToString(deserializedMessage.toString());
    }
}