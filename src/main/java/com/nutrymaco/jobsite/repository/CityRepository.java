package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
    City findByName(String name);
}
