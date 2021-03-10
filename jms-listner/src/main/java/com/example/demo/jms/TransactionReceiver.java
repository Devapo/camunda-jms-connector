package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;
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
        runtimeService.createMessageCorrelation(convertedMessage)
                .processInstanceBusinessKey("key")
                .correlate();

        //runtimeService.startpr
    }
}