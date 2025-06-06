package com.example.demo.dto;

import lombok.Data;

@Data
public class Header {
    private String reqId;
    private String integrationSystem;
    private String requestDatetime;
    private String authorization;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getIntegrationSystem() {
        return integrationSystem;
    }

    public void setIntegrationSystem(String integrationSystem) {
        this.integrationSystem = integrationSystem;
    }

    public String getRequestDatetime() {
        return requestDatetime;
    }

    public void setRequestDatetime(String requestDatetime) {
        this.requestDatetime = requestDatetime;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
