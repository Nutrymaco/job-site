package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
    City findByName(String name);
    List<City> findAllByIdIn(List<Integer> idList);
}
