package com.example.demo.jms;

import com.example.demo.entity.Transaction;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionReceiver {
    @JmsListener(destination = "test", containerFactory = "myFactory")
    public void receiveMessage(Transaction transaction) {
        System.out.println("Received <" + transaction + ">");
    }
}