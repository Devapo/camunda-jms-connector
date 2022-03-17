package com.example.demo.jms;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class JmsListenerConfiguration {

    private final String instanceId;
    private final String payload;

    public JmsListenerConfiguration(
            @Value("${json.instanceid}") String instanceId,
            @Value("${json.payload}") String payload) {
        this.instanceId = instanceId;
        this.payload = payload;
    }

    @Bean
    TransactionReceiver transactionReceiver(RuntimeService runtimeService) {
        return new TransactionReceiver(
                new JmsMsgDeserializer(instanceId, payload),
                new CamundaProcessStarter(runtimeService)
        );
    }
}
