package com.pavan.job_application.service.impl;

import com.pavan.job_application.dto.job.JobRequest;
import com.pavan.job_application.dto.job.JobResponse;
import com.pavan.job_application.exception.CompanyRequiredException;
import com.pavan.job_application.model.Company;
import com.pavan.job_application.model.Job;
import com.pavan.job_application.repository.CompanyRepository;
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
    private final CompanyRepository companyRepository;

    @Override
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        return mapToResponse(job);
    }

    @Override
    @Transactional
    public JobResponse createJob(Long companyId, JobRequest jobRequest) {

        BigDecimal minSalary = jobRequest.getMinSalary();
        BigDecimal maxSalary = jobRequest.getMaxSalary();

        if (minSalary != null && maxSalary != null && minSalary.compareTo(maxSalary) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Minimum salary must not be greater than maximum salary");
        }

        if (companyId == null) {
            throw new CompanyRequiredException();
        }

        Job job = Job.builder()
                .title(jobRequest.getTitle())
                .description(jobRequest.getDescription())
                .maxSalary(maxSalary)
                .minSalary(minSalary)
                .location(jobRequest.getLocation())
                .company(findCompany(companyId))
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
    @Transactional
    public Boolean deleteJobById(Long id) {
        if(!jobRepository.existsById(id)) {
            return false;
        }
        jobRepository.deleteById(id);
        return true;
    }

    private Company findCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Company not found with id " + companyId));
    }

    private JobResponse mapToResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .location(job.getLocation())
                .companyId(job.getCompany() != null ? job.getCompany().getId() : null)
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }
}
