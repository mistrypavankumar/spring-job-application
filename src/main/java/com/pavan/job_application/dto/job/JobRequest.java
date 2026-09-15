package com.pavan.job_application.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {
    private String title;
    private String description;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String location;
}
