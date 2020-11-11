package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.Autosearch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AutosearchRepository extends CrudRepository<Autosearch, Integer> {

    List<Autosearch> findByTextAndExperienceAndSalary(
                                String text,
                                int experience,
                                int salary);

}
