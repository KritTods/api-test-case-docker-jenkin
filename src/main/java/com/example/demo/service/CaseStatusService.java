package com.example.demo.service;

import com.example.demo.dto.response.BodyEncryptedDto;
import com.example.demo.dto.request.CaseStatusRequest;
import com.example.demo.dto.response.BodyDecryptedDto;
import com.example.demo.dto.response.CaseStatusResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CaseStatusService {

    public CaseStatusResponse resolveCase(CaseStatusRequest request) {
        // ตัวอย่างแค่ส่ง response success คืน (คุณสามารถเพิ่มการถอดรหัสและเข้ารหัสได้ตามจริง)
        BodyEncryptedDto encrypted = new BodyEncryptedDto();
        encrypted.setEncryptedData(request.getBodyEncrypted().getEncryptedData());
        encrypted.setInitialVector(UUID.randomUUID().toString().toUpperCase());

        BodyDecryptedDto decrypted = BodyDecryptedDto.builder()
                .statusCode("200")
                .statusDescription("Success")
                .data(null)
                .build();

        CaseStatusResponse response = new CaseStatusResponse();
        response.setBodyEncrypted(encrypted);
        response.setBodyDecrypted(decrypted);
        return response;
    }
}

