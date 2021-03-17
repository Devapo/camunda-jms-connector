package com.example.demo.jms;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class CamundaProcessStarter {

    private final RuntimeService runtimeService;

    public void startProcessByMessage(DeserializedMessage msg) {
        runtimeService.createMessageCorrelation(msg.getPayload())
                .setVariable("ID", msg.getInstanceId())
                .setVariable("PAYLOAD", msg.getPayload())
                .processInstanceBusinessKey(msg.getInstanceId())
                .correlate();
    }
}
