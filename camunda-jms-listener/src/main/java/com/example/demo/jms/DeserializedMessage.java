package com.example.demo.jms;

public class DeserializedMessage {

    private final String instanceId;
    private final String payload;

    public DeserializedMessage(String instanceId, String payload) {
        this.instanceId = instanceId;
        this.payload = payload;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "DeserializedMessage{" +
                "instanceId='" + instanceId + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
