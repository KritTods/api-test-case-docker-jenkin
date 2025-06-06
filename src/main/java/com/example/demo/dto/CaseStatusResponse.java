package com.example.demo.dto;

import com.example.demo.dto.request.BodyEncryptedDto;
import lombok.Data;

@Data
public class CaseStatusResponse {
    private BodyEncryptedDto bodyEncrypted;
    private CaseStatusDecryptedDto bodyDecrypted;
}
