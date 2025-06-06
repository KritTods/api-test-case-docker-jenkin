package com.example.demo.service;

import com.example.demo.dto.CaseData;
import com.example.demo.dto.request.BodyEncryptedDto;
import com.example.demo.dto.request.CaseStatusRequest;
import com.example.demo.dto.response.BodyDecryptedDto;
import com.example.demo.dto.response.CaseStatusResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaseTimelineServiceTest {

    private final CaseTimelineService service = new CaseTimelineService();

    @Test
    void testRetrieveCaseTimeline_returnsExpectedData() {
        // Arrange
        BodyEncryptedDto encryptedDto = new BodyEncryptedDto();
        encryptedDto.setInitialVector("test-vector-123");

        CaseStatusRequest request = new CaseStatusRequest();
        request.setBodyEncrypted(encryptedDto);

        // Act
        CaseStatusResponse response = service.retrieveCaseTimeline(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBodyDecrypted());
        assertEquals("200", response.getBodyDecrypted().getStatusCode());

        List<CaseData> dataList = (List<CaseData>) response.getBodyDecrypted().getData();
        assertEquals(1, dataList.size());
        assertEquals("CASE123456", dataList.get(0).getCaseId());
    }
}
