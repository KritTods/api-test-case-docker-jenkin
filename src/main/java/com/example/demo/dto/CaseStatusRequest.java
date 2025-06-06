package com.example.demo.dto;

import com.example.demo.dto.request.BodyEncryptedDto;
import com.example.demo.dto.request.HeaderDto;
import lombok.Data;

@Data
public class CaseStatusRequest {
    private HeaderDto header;
    private BodyEncryptedDto bodyEncrypted;
}

