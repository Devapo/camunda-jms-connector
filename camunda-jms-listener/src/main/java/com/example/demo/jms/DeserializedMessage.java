package com.example.demo.jms;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeserializedMessage {
    private final String instanceId;
    private final String payload;
}
