package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.dto.CompanyDTO;
import com.nutrymaco.jobsite.dto.filter.Option;
import com.nutrymaco.jobsite.entity.Company;
import com.nutrymaco.jobsite.exception.found.CompanyNotFoundException;
import com.nutrymaco.jobsite.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public CompanyDTO patchById(int id, CompanyDTO patch) throws CompanyNotFoundException {
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

        return toDTO(companyRepository.save(company.get()));
    }


    @Override
    public CompanyDTO toDTO(Company company) {
        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .approved(company.getApproved())
                .build();
    }

    @Override
    public Company fromDTO(CompanyDTO dto) {
        Optional<Company> existedCompany = companyRepository.findById(dto.getId());
        if (existedCompany.isEmpty()) {
            return Company.builder()
                    .id(dto.getId())
                    .approved(dto.getApproved())
                    .name(dto.getName())
                    .documents(new ArrayList<>())
                    .employees(new ArrayList<>())
                    .build();
        } else {
            Company company = existedCompany.get();
            if (dto.getName() != null)
                company.setName(dto.getName());
            if (dto.getApproved() != null)
                company.setApproved(dto.getApproved());
            return company;
        }
    }

    @Override
    public CompanyDTO updateById(int id, CompanyDTO update) {
        update.setId(id);
        return toDTO(companyRepository.save(fromDTO(update)));
    }
}
