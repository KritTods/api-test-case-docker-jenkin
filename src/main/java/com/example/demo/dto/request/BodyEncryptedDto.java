package com.example.demo.dto.request;

import lombok.Data;

@Data
public class BodyEncryptedDto {
    private String encryptedData;
    private String initialVector;
}

