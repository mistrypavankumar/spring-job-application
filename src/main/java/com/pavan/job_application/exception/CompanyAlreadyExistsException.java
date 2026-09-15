package com.pavan.job_application.exception;

import org.springframework.http.HttpStatus;

public class CompanyAlreadyExistsException extends AppException {
    public CompanyAlreadyExistsException(String name) {
        super("Company Already exists with " + name,
                HttpStatus.BAD_REQUEST,
                "COMPANY_ALREADY_EXISTS"
        );
    }
}
