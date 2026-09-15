package com.pavan.job_application.exception;

import org.springframework.http.HttpStatus;

public class CompanyNotFoundException extends AppException {
    public CompanyNotFoundException(Long id) {
        super("Company not found with id: " + id,
                HttpStatus.BAD_REQUEST,
                "COMPANY_NOT_FOUND"
        );
    }
}
