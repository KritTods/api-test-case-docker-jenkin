package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class CaseStatusDecryptedDto {
    private String statusCode;
    private String statusDescription;
    private List<CaseData> data;
}

