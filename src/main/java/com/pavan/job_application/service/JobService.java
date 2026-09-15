package com.pavan.job_application.service;

import com.pavan.job_application.dto.job.JobRequest;
import com.pavan.job_application.dto.job.JobResponse;

import java.util.List;

public interface JobService {
    List<JobResponse> getAllJobs();
    JobResponse getJobById(Long id);
    JobResponse createJob(Long companyId, JobRequest jobRequest);
    JobResponse updateJobById(Long id, JobRequest jobRequest);
    Boolean deleteJobById(Long id);
}
