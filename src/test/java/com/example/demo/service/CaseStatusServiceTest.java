package com.example.demo.service;

import com.example.demo.dto.request.CaseStatusRequest;
import com.example.demo.dto.request.HeaderDto;
import com.example.demo.dto.request.BodyEncryptedDto;
import com.example.demo.dto.response.CaseStatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaseStatusServiceTest {

    private CaseStatusService caseStatusService;

    @BeforeEach
    void setUp() {
        caseStatusService = new CaseStatusService();
    }

    @Test
    void testResolveCase_ShouldReturnSuccessResponse() {
        // Arrange
        BodyEncryptedDto bodyEncrypted = new BodyEncryptedDto();
        bodyEncrypted.setEncryptedData("test-encrypted-data");
        bodyEncrypted.setInitialVector("test-iv");

        HeaderDto header = new HeaderDto();
        header.setReqId("1234-req-id");
        header.setAuthorization("Bearer dummy_token");
        header.setIntegrationSystem("TEP");
        header.setRequestDatetime("2025-05-26T12:00:00+07:00");

        CaseStatusRequest request = new CaseStatusRequest();
        request.setHeader(header);
        request.setBodyEncrypted(bodyEncrypted); // <-- ensure not null

        // Act
        CaseStatusResponse response = caseStatusService.resolveCase(request);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getBodyDecrypted(), "Decrypted body should not be null");
        assertEquals("200", response.getBodyDecrypted().getStatusCode());
        assertEquals("Success", response.getBodyDecrypted().getStatusDescription());

    }
}
