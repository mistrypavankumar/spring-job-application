package com.pavan.job_application.repository;

import com.pavan.job_application.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Boolean existsByName(String name);
}
