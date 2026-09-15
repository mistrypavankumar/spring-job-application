package com.pavan.job_application.controller;

import com.pavan.job_application.dto.job.JobRequest;
import com.pavan.job_application.dto.job.JobResponse;
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

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs(){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id){
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PostMapping("/{companyId}")
    public ResponseEntity<JobResponse> createJob(@PathVariable("companyId") Long companyId, @RequestBody JobRequest jobRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(companyId , jobRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(@PathVariable("id") Long id, @RequestBody JobRequest jobRequest){
        return ResponseEntity.status(HttpStatus.OK).body(jobService.updateJobById(id, jobRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable("id") Long id){

        if(jobService.deleteJobById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Job deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found");
    }
}
