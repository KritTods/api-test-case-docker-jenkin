package com.example.demo.dto.request;

import lombok.Data;

@Data
public class HeaderDto {
    private String reqId;
    private String integrationSystem;
    private String requestDatetime;
    private String authorization;
}

