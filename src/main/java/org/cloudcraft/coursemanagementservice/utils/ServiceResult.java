package org.cloudcraft.coursemanagementservice.utils;


import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Data
@Builder
public class ServiceResult {
    private int statusCode;
    private String message;
    private ResultType resultTye;
}
