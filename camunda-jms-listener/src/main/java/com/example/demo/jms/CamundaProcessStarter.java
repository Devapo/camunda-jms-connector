package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;

public class CamundaProcessStarter {

    @Autowired
    RuntimeService runtimeService;

    public void startProcessByMessage(DeserializedMessage msg) {
        /*runtimeService.createMessageCorrelation(msg.getPayload())
                .setVariable("ID", msg.getInstanceId())
                .setVariable("PAYLOAD", msg.getPayload())
                .processInstanceBusinessKey(msg.getInstanceId())
                .correlate();*/
    }
}
