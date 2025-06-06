package com.example.demo.dto;

import lombok.Data;

@Data
public class BodyEncrypted {
    private String encryptedData;
    private String initialVector;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getInitialVector() {
        return initialVector;
    }

    public void setInitialVector(String initialVector) {
        this.initialVector = initialVector;
    }
}
