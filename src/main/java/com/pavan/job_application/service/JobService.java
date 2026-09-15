package com.pavan.job_application.service;

import com.pavan.job_application.dto.JobRequest;
import com.pavan.job_application.dto.JobResponse;

import java.util.List;

public interface JobService {
    List<JobResponse> getAllJobs();
    JobResponse createJob(JobRequest jobRequest);
}
