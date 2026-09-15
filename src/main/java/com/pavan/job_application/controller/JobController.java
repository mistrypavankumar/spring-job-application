package com.pavan.job_application.controller;

import com.pavan.job_application.dto.JobRequest;
import com.pavan.job_application.dto.JobResponse;
import com.pavan.job_application.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping("/")
    public ResponseEntity<List<JobResponse>> getAllJobs(){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getAllJobs());
    }

    @PostMapping("/create")
    public ResponseEntity<JobResponse> createJob(@RequestBody JobRequest jobRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(jobRequest));
    }
}
