package com.pavan.job_application.dto.company;

import com.pavan.job_application.dto.job.JobResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {
    private Long id;
    private String name;
    private String description;
    private List<JobResponse> job;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
