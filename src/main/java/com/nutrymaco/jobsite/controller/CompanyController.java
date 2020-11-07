package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.entity.Company;
import com.nutrymaco.jobsite.exception.found.CompanyNotFoundException;
import com.nutrymaco.jobsite.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @PostMapping("")
    ResponseEntity<?> addCompany(Company company) {
        Company savedCompany = companyService.save(company);
        return ResponseEntity
                .status(201)
                .body(savedCompany);
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> patchCompany(@PathVariable int id, Company company) throws CompanyNotFoundException {
        /*
            here will be logic of auth
         */
        Company patchedCompany = companyService.patchById(id, company);
        return ResponseEntity.ok(patchedCompany);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateCompany(@PathVariable int id, Company company) {
        Company updatedCompany = companyService.save(company);
        return ResponseEntity.ok(updatedCompany);
    }

}
