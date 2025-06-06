package com.example.demo.model;

import lombok.Data;

@Data
public class ConfirmCaseDecryptedResponse {
    private String statusCode;
    private String statusDescription;

    // Getters and Setters
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
