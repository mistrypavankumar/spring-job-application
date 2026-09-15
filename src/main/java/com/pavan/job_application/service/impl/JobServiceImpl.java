package com.pavan.job_application.service.impl;

import com.pavan.job_application.dto.JobRequest;
import com.pavan.job_application.dto.JobResponse;
import com.pavan.job_application.model.Job;
import com.pavan.job_application.repository.JobRepository;
import com.pavan.job_application.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobResponse> getAllJobs() {
        return List.of();
    }

    @Override
    public JobResponse createJob(JobRequest jobRequest) {

        BigDecimal minSalary = jobRequest.getMinSalary();
        BigDecimal maxSalary = jobRequest.getMaxSalary();

        if (minSalary != null && maxSalary != null && minSalary.compareTo(maxSalary) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Minimum salary must not be greater than maximum salary");
        }

        Job job = Job.builder()
                .title(jobRequest.getTitle())
                .description(jobRequest.getDescription())
                .maxSalary(maxSalary)
                .minSalary(minSalary)
                .location(jobRequest.getLocation())
                .build();

        job = jobRepository.save(job);
        return mapToResponse(job);
    }

    private JobResponse mapToResponse(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getMinSalary(),
                job.getMinSalary(),
                job.getLocation(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }
}
