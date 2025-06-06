package com.example.demo.service;

import com.example.demo.model.ConfirmCaseResponse;
import org.springframework.stereotype.Service;

@Service
public class CompleteCaseService {
    public ConfirmCaseResponse processRequest(String encryptedData, String initialVector) {
        // Mock decryption logic
        ConfirmCaseResponse response = new ConfirmCaseResponse();
        response.setStatusCode("200");
        response.setStatusDescription("Success");
        return response;
    }
}
