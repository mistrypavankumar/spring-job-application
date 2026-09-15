package com.pavan.job_application.dto.company;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    private Long id;
    private String name;
    private String description;
}
