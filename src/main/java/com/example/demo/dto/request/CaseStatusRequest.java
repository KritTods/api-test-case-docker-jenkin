package com.example.demo.dto.request;

import lombok.Data;

@Data
public class CaseStatusRequest {
    private HeaderDto header;
    private BodyEncryptedDto bodyEncrypted;
}
