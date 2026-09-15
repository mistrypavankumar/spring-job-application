package com.pavan.job_application.service.impl;

import com.pavan.job_application.dto.JobRequest;
import com.pavan.job_application.dto.JobResponse;
import com.pavan.job_application.model.Job;
import com.pavan.job_application.repository.JobRepository;
import com.pavan.job_application.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
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

    @Override
    @Transactional
    public JobResponse updateJobById(Long id, JobRequest jobRequest) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));

        BigDecimal minSalary = Objects.requireNonNullElse(jobRequest.getMinSalary(), job.getMinSalary());
        BigDecimal maxSalary = Objects.requireNonNullElse(jobRequest.getMaxSalary(), job.getMaxSalary());

        if (minSalary.compareTo(maxSalary) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Minimum salary must not be greater than maximum salary");
        }

        job.setTitle(Objects.requireNonNullElse(jobRequest.getTitle(), job.getTitle()));
        job.setDescription(Objects.requireNonNullElse(jobRequest.getDescription(), job.getDescription()));
        job.setLocation(Objects.requireNonNullElse(jobRequest.getLocation(), job.getLocation()));
        job.setMinSalary(minSalary);
        job.setMaxSalary(maxSalary);

        return mapToResponse(jobRepository.save(job));
    }

    @Override
    public Boolean deleteJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        jobRepository.deleteById(id);
        return true;
    }

    private JobResponse mapToResponse(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getMinSalary(),
                job.getMaxSalary(),
                job.getLocation(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }
}
