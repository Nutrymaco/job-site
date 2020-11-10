package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
    Optional<Company> findFirstByName(String name);
}
