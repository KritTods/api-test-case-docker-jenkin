package com.example.demo.service; // 🔧 ใช้ชื่อ package ให้ตรงกัน

import com.example.demo.dto.*;
import com.example.demo.util.AesEncryptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class CancelCaseService {

    private static final String SECRET_KEY = "1234567890123456"; // AES-128 key

    private final ObjectMapper objectMapper;

    public CancelCaseService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CancelCaseResponse cancelCase(CancelCaseRequest request) {
        BodyDecrypted bodydecrypted;
        try {
            String json = AesEncryptor.decrypt(
                    request.getBodyEncrypted().getEncryptedData(),
                    request.getBodyEncrypted().getInitialVector(),
                    SECRET_KEY
            );
            bodydecrypted = objectMapper.readValue(json, BodyDecrypted.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt request body", e);
        }

        ResponseBodyDecrypted decrypted = new ResponseBodyDecrypted();
        decrypted.setStatusCode("200");
        decrypted.setStatusDescription("Success");
        decrypted.setData(null);

        CancelCaseResponse response = new CancelCaseResponse();
        response.setBodyEncrypted(request.getBodyEncrypted()); // ถ้าต้องการเข้ารหัสใหม่ให้ใช้ AesEncryptor.encrypt(...)
        response.setBodyDecrypted(decrypted);
        return response;
    }
}


