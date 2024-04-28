package com.esprit.cloudcraft.utils;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResult {
    private int statusCode;
    private String message;
    private ResultType resultTye;
}
