package com.example.demo.jms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class JmsListenerConfiguration {

    @Value("${json.instanceid}")
    private String INSTANCE_ID;

    @Value("${json.payload}")
    private String PAYLOAD;

    @Bean
    JmsMsgDeserializer jmsMsgDeserializer() {
        return new JmsMsgDeserializer(INSTANCE_ID, PAYLOAD);
    }
}
