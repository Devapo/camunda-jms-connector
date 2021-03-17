package com.example.demo.jms;

import lombok.Data;

@Data
public class DeserializedMessage {
    private final String instanceId;
    private final String payload;

    @Override
    public String toString(){
        return "{ " + instanceId + ", " + payload + " }";
    }
}
