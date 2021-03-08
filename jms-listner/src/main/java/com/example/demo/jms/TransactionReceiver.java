package com.example.demo.jms;

import com.example.demo.entity.Transaction;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class TransactionReceiver {
    @JmsListener(destination = "test", containerFactory = "myFactory")
    public void receiveMessage(Message message) throws JMSException {
        String convertedMessage =((TextMessage) message).getText();
        System.out.println("Received <" + convertedMessage + ">");
    }
}