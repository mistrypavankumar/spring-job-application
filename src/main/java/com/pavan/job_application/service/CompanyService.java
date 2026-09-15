package com.pavan.job_application.service;

import com.pavan.job_application.dto.company.CompanyRequest;
import com.pavan.job_application.dto.company.CompanyResponse;

import java.util.List;

public interface CompanyService {
    List<CompanyResponse> getAllCompanies();

    CompanyResponse createCompany(CompanyRequest companyRequest);

    CompanyResponse updateCompanyById(Long id, CompanyRequest companyRequest);

    CompanyResponse getCompanyById(Long id);

    boolean deleteCompanyById(Long id);
}
