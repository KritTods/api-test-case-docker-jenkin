package com.example.demo.dto;

import lombok.Data;

@Data

public class BodyEncryptedDto {
    private String encryptedData;
    private String initialVector;
    // getter/setter
}

