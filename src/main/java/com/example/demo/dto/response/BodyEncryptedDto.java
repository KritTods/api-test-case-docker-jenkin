package com.example.demo.dto.response;

import lombok.Data;

@Data
public class BodyEncryptedDto {
    private String encryptedData;
    private String initialVector;
}

