package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BodyDecryptedDto {
    private String statusCode;
    private String statusDescription;
    private Object data;

    public BodyDecryptedDto() {
        // default constructor
    }

    public BodyDecryptedDto(String statusCode, String statusDescription, Object data) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.data = data;
    }
}


