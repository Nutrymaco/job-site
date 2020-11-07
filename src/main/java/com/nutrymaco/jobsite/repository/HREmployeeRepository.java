package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.HREmployee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HREmployeeRepository extends CrudRepository<HREmployee, String> {

    Optional<HREmployee> findByEmail(String email);

}
