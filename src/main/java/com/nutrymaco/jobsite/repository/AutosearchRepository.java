package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.Autosearch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface AutosearchRepository extends CrudRepository<Autosearch, Integer> {
    Optional<Autosearch> findByFilters(MultiValueMap<String, String> filters);
}
