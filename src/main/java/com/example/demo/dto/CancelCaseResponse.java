package com.example.demo.dto;

import lombok.Data;

@Data
public class CancelCaseResponse {
    private BodyEncrypted bodyEncrypted;
    private ResponseBodyDecrypted bodyDecrypted;

    public BodyEncrypted getBodyEncrypted() {
        return bodyEncrypted;
    }

    public void setBodyEncrypted(BodyEncrypted bodyEncrypted) {
        this.bodyEncrypted = bodyEncrypted;
    }

    public ResponseBodyDecrypted getBodyDecrypted() {
        return bodyDecrypted;
    }

    public void setBodyDecrypted(ResponseBodyDecrypted bodyDecrypted) {
        this.bodyDecrypted = bodyDecrypted;
    }
}
