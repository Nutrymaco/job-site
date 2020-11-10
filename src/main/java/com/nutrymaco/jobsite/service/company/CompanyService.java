package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.dto.CompanyDTO;
import com.nutrymaco.jobsite.entity.Company;
import com.nutrymaco.jobsite.exception.found.CompanyNotFoundException;
import org.springframework.boot.CommandLineRunner;

public interface CompanyService {
    CompanyDTO save(CompanyDTO company);
    CompanyDTO patchById(int id, CompanyDTO patch) throws CompanyNotFoundException;
    CompanyDTO updateById(int id, CompanyDTO update);
    CompanyDTO toDTO(Company company);
    Company fromDTO(CompanyDTO dto);
    CompanyDTO getById(Integer id) throws CompanyNotFoundException;
    CompanyDTO getFirstByName(String name) throws CompanyNotFoundException;
}
