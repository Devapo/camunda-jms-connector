package com.example.demo.controllers;


import com.example.demo.entity.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    //private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final JmsTemplate jmsTemplate;

    public TransactionController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("/send")
    public void send(@RequestBody Transaction transaction) {
        //log.info("Sending a transaction.");
        System.out.println("SEND");
        jmsTemplate.convertAndSend("test", transaction);
    }
}