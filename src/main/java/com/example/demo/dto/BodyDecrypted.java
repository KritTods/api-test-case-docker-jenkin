package com.example.demo.dto;

import lombok.Data;

@Data
public class BodyDecrypted {
    private String caseNumber;
    private String employeeId;
    private String cancelReasonCode;
    private String cancelReasonComment;

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCancelReasonCode() {
        return cancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        this.cancelReasonCode = cancelReasonCode;
    }

    public String getCancelReasonComment() {
        return cancelReasonComment;
    }

    public void setCancelReasonComment(String cancelReasonComment) {
        this.cancelReasonComment = cancelReasonComment;
    }
}