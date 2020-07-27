package com.nutrymaco.jobsite.service;

import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface Service<DTO, E> {
    E save(DTO dto);
    boolean remove(DTO dto);
    Optional<DTO> load(int id);
    Optional<List<E>> loadValues(MultiValueMap<String, String> map);
    DTO toDTO(E entity);
    E fromDTO(DTO dto);
}
