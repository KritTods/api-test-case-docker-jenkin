package com.example.demo.dto;

import lombok.Data;

@Data
public class CancelCaseRequest {
    private BodyEncrypted bodyEncrypted;
    private BodyDecrypted bodyDecrypted;
    private Header header;

    public BodyEncrypted getBodyEncrypted() {
        return bodyEncrypted;
    }

    public void setBodyEncrypted(BodyEncrypted bodyEncrypted) {
        this.bodyEncrypted = bodyEncrypted;
    }

    public BodyDecrypted getBodyDecrypted() {
        return bodyDecrypted;
    }

    public void setBodyDecrypted(BodyDecrypted bodyDecrypted) {
        this.bodyDecrypted = bodyDecrypted;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
