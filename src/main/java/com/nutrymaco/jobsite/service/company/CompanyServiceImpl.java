package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.entity.Company;
import com.nutrymaco.jobsite.exception.found.CompanyNotFoundException;
import com.nutrymaco.jobsite.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Company save(Company company) {
        company.setApproved(false);
        return companyRepository.save(company);
    }

    @Override
    public Company patchById(int id, Company patch) throws CompanyNotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new CompanyNotFoundException();
        }

        if (patch.getName() != null) {
            company.get().setName(patch.getName());
        }
        if (patch.getApproved() != null) {
            company.get().setApproved(patch.getApproved());
        }

        return companyRepository.save(company.get());
    }

    @Override
    public Company updateById(int id, Company update) {
        update.setId(id);
        return companyRepository.save(update);
    }
}
