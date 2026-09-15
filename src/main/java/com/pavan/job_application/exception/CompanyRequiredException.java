package com.pavan.job_application.exception;

import org.springframework.http.HttpStatus;

public class CompanyRequiredException extends AppException {
    public CompanyRequiredException() {
        super("Company Id is required",
                HttpStatus.BAD_REQUEST,
                "COMPANY_ID_REQUIRED");
    }
}
