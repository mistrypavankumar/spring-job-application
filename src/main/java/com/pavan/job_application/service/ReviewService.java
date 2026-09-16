package com.pavan.job_application.service;

import com.pavan.job_application.dto.review.ReviewRequest;
import com.pavan.job_application.dto.review.ReviewResponse;
import com.pavan.job_application.model.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> getAllReviewsByCompanyById(Long companyId);

    ReviewResponse createReviewByCompanyId(Long companyId, ReviewRequest reviewRequest);

    ReviewResponse updateCompanyReviewById(Long reviewId, Long companyId, ReviewRequest reviewRequest);

    boolean deleteCompanyReviewById(Long reviewId, Long companyId);
}
