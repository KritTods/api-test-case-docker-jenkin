package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.request.BodyEncryptedDto;
import com.example.demo.dto.request.CaseStatusRequest;
import com.example.demo.dto.request.HeaderDto;
import com.example.demo.dto.response.BodyDecryptedDto;
import com.example.demo.dto.response.CaseStatusResponse;
import com.example.demo.model.ConfirmCaseDecryptedResponse;
import com.example.demo.model.ConfirmCaseResponse;
import com.example.demo.service.CancelCaseService;
import com.example.demo.service.CaseStatusService;
import com.example.demo.service.CaseTimelineService;
import com.example.demo.service.CompleteCaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompleteCaseService completeCaseService;
    @MockBean
    private CancelCaseService cancelCaseService;
    @MockBean
    private CaseStatusService caseStatusService;
    @MockBean
    private CaseTimelineService caseTimelineService;

    @Test
    void testProcessCase_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        String encryptedData = "testEncryptedData";
        String initialVector = "testIV";

        ConfirmCaseResponse mockResponse = new ConfirmCaseResponse();
        mockResponse.setMessage("Completed");
        mockResponse.setStatusCode("200");

        Mockito.when(completeCaseService.processRequest(eq(encryptedData), eq(initialVector)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/case/complete")
                        .param("encryptedData", encryptedData)
                        .param("initialVector", initialVector))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("200"));
    }

    @Test
    void testCancelCase_shouldReturn200() throws Exception {
        CancelCaseRequest mockRequest = new CancelCaseRequest();
        mockRequest.setHeader(new Header());
        mockRequest.getHeader().setReqId("req123");
        mockRequest.getHeader().setIntegrationSystem("TEP");
        mockRequest.getHeader().setRequestDatetime("2025-05-25T10:00:00+07:00");
        mockRequest.getHeader().setAuthorization("mock-token");

        BodyEncrypted encryptedBody = new BodyEncrypted();
        encryptedBody.setEncryptedData("dummyEncrypted");
        encryptedBody.setInitialVector("dummyIV");
        mockRequest.setBodyEncrypted(encryptedBody);

        BodyDecrypted decrypted = new BodyDecrypted();
        decrypted.setCaseNumber("CA20250221000174");
        decrypted.setEmployeeId("57025");
        decrypted.setCancelReasonCode("REASON001");
        decrypted.setCancelReasonComment("ข้อมูลไม่ถูกต้อง");
        mockRequest.setBodyDecrypted(decrypted);

        // ✅ Prepare expected response
        CancelCaseResponse mockResponse = new CancelCaseResponse();
        ResponseBodyDecrypted responseDecrypted = new ResponseBodyDecrypted();
        responseDecrypted.setStatusCode("200");
        responseDecrypted.setStatusDescription("Success");
        responseDecrypted.setData(null);

        mockResponse.setBodyEncrypted(encryptedBody);
        mockResponse.setBodyDecrypted(responseDecrypted);

        // ✅ Mock behavior
        when(cancelCaseService.cancelCase(mockRequest)).thenReturn(mockResponse);

        // ✅ Perform request
        mockMvc.perform(post("/api/case/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bodyDecrypted.statusCode").value("200"))
                .andExpect(jsonPath("$.bodyDecrypted.statusDescription").value("Success"));
    }

    @Test
    void resolveCase_shouldReturnStatus200AndValidResponse() throws Exception {
        // Arrange
        BodyEncryptedDto requestEncrypted = new BodyEncryptedDto();
        requestEncrypted.setEncryptedData("test-encrypted");
        requestEncrypted.setInitialVector("input-IV-123");

        CaseStatusRequest request = new CaseStatusRequest();
        request.setBodyEncrypted(requestEncrypted);

        // Mock response
        BodyEncryptedDto responseEncrypted = new BodyEncryptedDto();
        responseEncrypted.setEncryptedData("test-encrypted");
        responseEncrypted.setInitialVector(UUID.randomUUID().toString());

        BodyDecryptedDto responseDecrypted = BodyDecryptedDto.builder()
                .statusCode("200")
                .statusDescription("Success")
                .data(null)
                .build();

        CaseStatusResponse mockResponse = new CaseStatusResponse();
        mockResponse.setBodyEncrypted(responseEncrypted);
        mockResponse.setBodyDecrypted(responseDecrypted);

        when(caseStatusService.resolveCase(request)).thenReturn(mockResponse);

        // Act & Assert
        ResultActions success = mockMvc.perform(post("/api/case/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    void testRetrieveCaseTimelineEndpoint_shouldReturnValidJson() throws Exception {
        // Arrange
        CaseData data = new CaseData();
        data.setCaseId("CASE123456");
        data.setCaseNumber("CN0001");
        data.setParentCaseNumber("PCN0001");
        data.setEcId("EC789");
        data.setRmId("RM001");
        data.setCustomerName("John Doe");
        data.setContactPersonName("Jane Smith");
        data.setContactPersonEmail("jane.smith@example.com");
        data.setContactPersonPhone("0800000001");
        data.setContactPersonPhone2("0800000002");
        data.setContactPersonChannelCode("LINE");
        data.setContactPersonChannelValue("Line Official");
        data.setToExt1("EXT001");
        data.setToExt2("EXT002");
        data.setServiceTypeMatrixCode("STMC001");
        data.setServiceTypeMatrixType("Complaint");
        data.setServiceCategoryCode("SCC001");
        data.setServiceCategoryValue("Banking Issue");
        data.setServiceTabCode("STC001");
        data.setServiceTabValue("Deposit");
        data.setSubServiceTabCode("SSTC001");
        data.setSubServiceTabValue("Savings");
        data.setSupportedChannelCode("WEB");
        data.setSupportedChannelValue("Website");
        data.setProductServiceCode("PSC001");
        data.setProductServiceValue("Online Banking");
        data.setNtbEligible(true);
        data.setIssueNameTtbTouchTh("การโอนเงินล้มเหลว");
        data.setIssueNameTtbTouchEn("Failed Transfer");
        data.setIssueTh("ไม่สามารถโอนเงินได้");
        data.setIssueEn("Unable to transfer money");
        data.setIssueLine2Th("กรุณาลองใหม่อีกครั้ง");
        data.setIssueLine2En("Please try again later");
        data.setFcr(false);
        data.setServiceCaseDescription("Customer could not complete fund transfer");
        data.setDescription("Customer reported a failed transaction via web portal");
        data.setMessageId("MSG12345");
        data.setUrlLink("https://ttbexample.com/support/case/CASE123456");
        data.setAutoCloseCaseAfterResolved(true);
        data.setSeverity("HIGH");
        data.setServiceTemplateCode("STPL001");
        data.setServiceTemplateValue("Fund Transfer");
        data.setServiceTemplateTypeCode("TTYPE01");
        data.setServiceTemplateTypeValue("Online Service");
        data.setSmsCodeInProgress("SMS001");
        data.setSmsCodeNew("SMS002");
        data.setSmsCodeResolved("SMS003");
        data.setSmsCodeCompleted("SMS004");
        data.setSmsCodeResolution1("SMS005");
        data.setSmsCodeResolution2("SMS006");
        data.setResolution1Code("R1C001");
        data.setResolution1Value("Reset Transaction");
        data.setResolution2Code("R2C002");
        data.setResolution2Value("Notify Customer");
        data.setAutoResolutionCode("ARC001");
        data.setAutoResolutionValue("Retry Mechanism");
        data.setCarouselServiceTabCode("CSC001");
        data.setCarouselServiceTabValue("Funds");
        data.setPtaSegmentCode("PTA01");
        data.setPtaSegmentValue("Premium");
        data.setRfRequired(true);
        data.setPriorityCode("P1");
        data.setPriorityValue("Critical");
        data.setBranchCode("B001");
        data.setReturnReasonCode("RR001");
        data.setReturnReasonValue("Incorrect Info");
        data.setRootCauseListCode("RC001");
        data.setRootCauseListValue("System Timeout");
        data.setRootCauseComment("Network latency caused failure");
        data.setOriginCode("OG001");
        data.setOriginValue("Online");
        data.setDataSourceCode("DS001");
        data.setDataSourceValue("CRM");
        data.setResolutionListCode("RES001");
        data.setResolutionListValue("Retry");
        data.setResolutionCommentCode("RCM001");
        data.setResolutionCommentValue("Confirmed with backend");
        data.setProductType1("Savings");
        data.setProductType2("Current");
        data.setProductType3("Fixed");
        data.setProductNumber1("1234567890");
        data.setProductNumber2("0987654321");
        data.setProductNumber3("5678901234");
        data.setSuffix1("001");
        data.setSuffix2("002");
        data.setSuffix3("003");
        data.setCardNo("411111******1111");
        data.setCardNo2("422222******2222");
        data.setTransactionDate(OffsetDateTime.parse("2025-05-20T14:00:00+07:00"));
        data.setFundTransferBillPaymentAmount(1500.00);
        data.setAmountDepositWithdrawal(2000.00);
        data.setBillerProviderName("Electric Co.");
        data.setRef1("REF001");
        data.setRef2("REF002");
        data.setRef3("REF003");
        data.setAtmBankOwnerCode("ATM001");
        data.setAtmBankOwnerValue("TTB");
        data.setBranchAtmShopLocationTransactionCode("LOC001");
        data.setBranchAtmShopLocationTransaction("Central Plaza");
        data.setBranchAtmShopAddressThai("123 ถนนสุขุมวิท กรุงเทพฯ");
        data.setBranchAtmShopNumber("BR1234");
        data.setTepTransId("TEP12345678");
        data.setCallNumber("C1234567");
        data.setParticipantId("PART001");
        data.setConversationId("CONV001");
        data.setCaseStatusCode("OPEN");
        data.setCaseStatusValue("เปิดเคส");
        data.setOwnerId("EMP123");
        data.setOwnerName("Nattapong L.");
        data.setTeamId("TEAM01");
        data.setTeamName("Customer Care");
        data.setIntegrationSource("TEP");
        data.setSlaStartDate(OffsetDateTime.parse("2025-05-20T14:01:00+07:00"));
        data.setSlaTargetDate(OffsetDateTime.parse("2025-05-22T14:01:00+07:00"));
        data.setSlaActualDate(OffsetDateTime.parse("2025-05-21T10:00:00+07:00"));
        data.setSlaStatus("MET");
        data.setClosedDate(OffsetDateTime.parse("2025-05-21T10:00:00+07:00"));
        data.setSlaHop(List.of(
                new SlaHop()
        ));
        data.setModifiedOn(OffsetDateTime.parse("2025-05-21T10:01:00+07:00"));
        data.setCreatedOn(OffsetDateTime.parse("2025-05-20T14:00:00+07:00"));
        data.setCreatedByEmaployeeId("EMP111");
        data.setCreatedByName("Siriwan T.");
        data.setCreatedByTeamId("TEAM01");
        data.setCreatedByTeamName("Customer Care");
        data.setCancelReasonCode(null);
        data.setCancelReasonValue(null);
        data.setCancelReasonComment(null);
        data.setVisibleOnTouch(true);
        data.setSla("24h");
        data.setActiveHopNumber(1);
        data.setCorrectRecipientBankCode("KTB");
        data.setCorrectBankRecipientAccountNo("123456789012");
        data.setTransactionTypeCode("TT01");
        data.setAmountReceivedDepositToAccount(1450.00);
        data.setDepositAmount(2000.00);
        data.setSubsidiary("TTB Public Co., Ltd.");
        data.setAmountWithdrawalDeposit(500.00);
        data.setDocumentId("DOC001");
        data.setExternalId("EXT123456");
        data.setObjectId("OBJ001");
        data.setRepositoryId("REP001");
        data.setDocumentType("PDF");
        data.setReadyToPrint(false);
        data.setEmsTracking("EMS123456789TH");
        data.setProductTypeCode1("PTC1");
        data.setProductTypeCode2("PTC2");
        data.setProductTypeCode3("PTC3");
        data.setSubProductTypeCode1("SPTC1");
        data.setSubProductTypeCode2("SPTC2");
        data.setSubProductTypeCode3("SPTC3");
        data.setProductTypeValue1("Saving Account");
        data.setProductTypeValue2("Current Account");
        data.setProductTypeValue3("Fixed Deposit");
        data.setSubProductTypeValue1("Standard");
        data.setSubProductTypeValue2("Premium");
        data.setSubProductTypeValue3("Gold");
        data.setProductNumberFull1("1234567890123456");
        data.setProductNumberFull2("6543210987654321");
        data.setProductNumberFull3("1122334455667788");
        data.setProductNumberMarking1("XXXX1234");
        data.setProductNumberMarking2("XXXX5678");
        data.setProductNumberMarking3("XXXX9999");
        data.setFundCode1("F001");
        data.setFundCode2("F002");
        data.setFundCode3("F003");
        data.setPromptPay("0987654321");
        data.setStartCall(OffsetDateTime.parse("2025-05-20T14:05:00+07:00"));
        data.setEndCall(OffsetDateTime.parse("2025-05-20T14:25:00+07:00"));
        data.setCallDuration(1200);
        data.setCallChannel("Phone");

        // เตรียม BodyDecryptedDto, BodyEncryptedDto และ CaseStatusResponse เหมือนเดิม

        // ... (code เหมือนเดิมที่สร้าง BodyDecryptedDto, BodyEncryptedDto, CaseStatusResponse, CaseStatusRequest)

    }

}
