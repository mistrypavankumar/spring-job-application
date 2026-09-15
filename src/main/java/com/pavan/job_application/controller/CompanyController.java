package com.pavan.job_application.controller;

import com.pavan.job_application.dto.company.CompanyRequest;
import com.pavan.job_application.dto.company.CompanyResponse;
import com.pavan.job_application.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;


    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(companyRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable("id") Long id, @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.updateCompanyById(id, companyRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable("id") Long id) {
        if(companyService.deleteCompanyById(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Delete companies successfully");
        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found");
    }


}
