package com.example.demo.service;

import com.example.demo.model.ConfirmCaseResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompleteCaseServiceTest {
    @Test
    public void testProcessRequest() {
        CompleteCaseService service = new CompleteCaseService();
        ConfirmCaseResponse response = service.processRequest("mockEncrypted", "mockIV");

        assertEquals("200", response.getStatusCode());
        assertEquals("Success", response.getStatusDescription());
    }
}
