package com.pavan.job_application.service.impl;

import com.pavan.job_application.dto.company.CompanyRequest;
import com.pavan.job_application.dto.company.CompanyResponse;
import com.pavan.job_application.dto.job.JobResponse;
import com.pavan.job_application.exception.CompanyAlreadyExistsException;
import com.pavan.job_application.exception.CompanyNotFoundException;
import com.pavan.job_application.model.Company;
import com.pavan.job_application.model.Job;
import com.pavan.job_application.repository.CompanyRepository;
import com.pavan.job_application.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;


    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponse> getAllCompanies() {
        List<Company> company = companyRepository.findAll();
        return company.stream()
                .map(this::mapToCompanyAndJobsResponse)
                .toList();
    }

    @Override
    @Transactional
    public CompanyResponse createCompany(CompanyRequest companyRequest) {

       Boolean isCompanyExists =  companyRepository.existsByName(companyRequest.getName());

       if(isCompanyExists){
           throw new CompanyAlreadyExistsException(companyRequest.getName());
       }

       Company company = Company.builder()
               .name(companyRequest.getName())
               .description(companyRequest.getDescription())
               .build();

       company = companyRepository.save(company);
       return mapToCompanyResponse(company);
    }

    @Override
    public CompanyResponse updateCompanyById(Long id, CompanyRequest companyRequest) {
        Company existingCompany =  companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));

        existingCompany.setName(Objects.requireNonNullElse(companyRequest.getName(), existingCompany.getName()));
        existingCompany.setDescription(Objects.requireNonNullElse(companyRequest.getDescription(), existingCompany.getDescription()));
        return mapToCompanyAndJobsResponse(companyRepository.save(existingCompany));
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        return mapToCompanyAndJobsResponse(company);
    }

    @Override
    @Transactional
    public boolean deleteCompanyById(Long id) {
        if(!companyRepository.existsById(id)){
            return false;
        }
        companyRepository.deleteById(id);
        return true;
    }

    private CompanyResponse mapToCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }

    private CompanyResponse mapToCompanyAndJobsResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .job(company.getJobs().stream().map(this::mapToCompanyJobResponse).toList())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }

    private JobResponse mapToCompanyJobResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .location(job.getLocation())
                .companyId(job.getCompany().getId())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }
}
