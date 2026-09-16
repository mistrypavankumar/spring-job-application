package com.pavan.job_application.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private String title;
    private String description;
    private BigDecimal rating;
}
