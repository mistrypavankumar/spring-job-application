package com.pavan.job_application.exception;

import org.springframework.http.HttpStatus;

public class ReviewNotFoundException extends AppException {
    public ReviewNotFoundException(Long reviewId, Long companyId) {
        super("Could not find review with id " + reviewId + " and company id " + companyId,
                HttpStatus.BAD_REQUEST,
                "REVIEW_NOT_FOUND"
        );
    }
}
