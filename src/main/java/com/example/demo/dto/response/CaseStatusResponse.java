package com.example.demo.dto.response;

import lombok.Data;

@Data
public class CaseStatusResponse {
    private BodyEncryptedDto bodyEncrypted;
    private BodyDecryptedDto bodyDecrypted;

    public void setBodyEncrypted(com.example.demo.dto.request.BodyEncryptedDto responseEncrypted) {
    }
    public void setBodyEncrypted(BodyEncryptedDto encrypted) {
    }
}

