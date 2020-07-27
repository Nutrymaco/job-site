package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {
}
