package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.entity.Company;
import com.nutrymaco.jobsite.exception.found.CompanyNotFoundException;

public interface CompanyService {
    Company save(Company company);
    Company patchById(int id, Company patch) throws CompanyNotFoundException;
    Company updateById(int id, Company update);
}
