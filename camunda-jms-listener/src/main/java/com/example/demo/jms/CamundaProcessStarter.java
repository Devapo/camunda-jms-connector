package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;

public class CamundaProcessStarter {

    private final RuntimeService runtimeService;

    public CamundaProcessStarter(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public void startProcessByMessage(DeserializedMessage msg) {
        runtimeService.createMessageCorrelation(msg.getPayload())
                .setVariable("ID", msg.getInstanceId())
                .setVariable("PAYLOAD", msg.getPayload())
                .processInstanceBusinessKey(msg.getInstanceId())
                .correlate();
    }
}
