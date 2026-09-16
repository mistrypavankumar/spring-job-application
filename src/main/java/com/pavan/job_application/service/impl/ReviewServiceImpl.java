package com.pavan.job_application.service.impl;

import com.pavan.job_application.dto.company.CompanyResponse;
import com.pavan.job_application.dto.review.ReviewRequest;
import com.pavan.job_application.dto.review.ReviewResponse;
import com.pavan.job_application.exception.CompanyNotFoundException;
import com.pavan.job_application.exception.ReviewNotFoundException;
import com.pavan.job_application.model.Company;
import com.pavan.job_application.model.Review;
import com.pavan.job_application.repository.ReviewRepository;
import com.pavan.job_application.service.CompanyService;
import com.pavan.job_application.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    @Override
    public List<ReviewResponse> getAllReviewsByCompanyById(Long companyId) {
        if(companyService.getCompanyById(companyId) == null) {
            throw new CompanyNotFoundException(companyId);
        }

        List<Review> allReviews = reviewRepository.findAllByCompanyId(companyId);
        return allReviews.stream()
                .map(this::mapToReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewResponse createReviewByCompanyId(Long companyId, ReviewRequest reviewRequest) {
        CompanyResponse company = companyService.getCompanyById(companyId);

        Review newReview = Review.builder()
                .title(reviewRequest.getTitle())
                .description(reviewRequest.getDescription())
                .company(mapToCompany(company))
                .rating(reviewRequest.getRating())
                .build();

        newReview = reviewRepository.save(newReview);
        return mapToReviewResponse(newReview);
    }

    private Company mapToCompany(CompanyResponse company) {
        return Company.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .build();

    }

    @Override
    @Transactional
    public ReviewResponse updateCompanyReviewById(Long reviewId, Long companyId, ReviewRequest reviewRequest) {
        if(companyService.getCompanyById(companyId) == null) {
            throw new CompanyNotFoundException(companyId);
        }

        Review review = reviewRepository.findByIdAndCompanyId(reviewId, companyId);

        if(Objects.isNull(review)) {
            throw new ReviewNotFoundException(reviewId, companyId);
        }

        review.setTitle(Objects.requireNonNullElse(reviewRequest.getTitle(), review.getTitle()));
        review.setDescription(Objects.requireNonNullElse(reviewRequest.getDescription(), review.getDescription()));
        review.setRating(Objects.requireNonNullElse(reviewRequest.getRating(), review.getRating()));
        review = reviewRepository.save(review);

        return mapToReviewResponse(review);
    }

    @Override
    @Transactional
    public boolean deleteCompanyReviewById(Long reviewId, Long companyId) {
        if(companyService.getCompanyById(companyId) == null) {
            throw new CompanyNotFoundException(companyId);
        }

        Review review = reviewRepository.findByIdAndCompanyId(reviewId, companyId);

        if(Objects.isNull(review)) {
            throw new ReviewNotFoundException(reviewId, companyId);
        }

        reviewRepository.delete(review);

        return true;
    }

    private ReviewResponse mapToReviewResponse(Review newReview) {
        return ReviewResponse.builder()
                .id(newReview.getId())
                .title(newReview.getTitle())
                .description(newReview.getDescription())
                .rating(newReview.getRating())
                .companyId(newReview.getCompany().getId())
                .createdAt(newReview.getCreatedAt())
                .updatedAt(newReview.getUpdatedAt())
                .build();
    }

}
