package com.pavan.job_application.repository;

import com.pavan.job_application.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.company.id = :companyId")
    List<Review> findAllByCompanyId(@Param("companyId") Long companyId);

    Review findByIdAndCompanyId(Long reviewId, Long companyId);
}
