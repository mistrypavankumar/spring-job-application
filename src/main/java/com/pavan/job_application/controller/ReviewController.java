package com.pavan.job_application.controller;

import com.pavan.job_application.dto.review.ReviewRequest;
import com.pavan.job_application.dto.review.ReviewResponse;
import com.pavan.job_application.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies/{companyId}")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsOfCompany(@PathVariable Long companyId){
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getAllReviewsByCompanyById(companyId));
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponse> createReviewByCompanyId(@PathVariable Long companyId, @RequestBody ReviewRequest reviewRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReviewByCompanyId(companyId, reviewRequest));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateCompanyReviewByid(
            @PathVariable Long companyId, @PathVariable Long reviewId, @RequestBody ReviewRequest reviewRequest){
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateCompanyReviewById(reviewId, companyId, reviewRequest));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteCompanyReviewByid(@PathVariable Long companyId, @PathVariable Long reviewId){

        if(reviewService.deleteCompanyReviewById(reviewId, companyId)){
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted Review");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
    }

}
