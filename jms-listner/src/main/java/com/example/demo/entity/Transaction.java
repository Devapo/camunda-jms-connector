package com.example.demo.entity;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private long id;
    private String from;
    private String to;
    private String payload;
}
