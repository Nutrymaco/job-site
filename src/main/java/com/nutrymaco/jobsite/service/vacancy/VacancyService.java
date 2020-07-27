package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.exception.validation.ValidationException;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public interface VacancyService {
    Vacancy save(VacancyDTO dto) throws ValidationException;
    void removeById(String id);
    void removeAll();
    Optional<Vacancy> load(String id);
    List<Vacancy> loadValues(MultiValueMap<String, String> map) throws ValidationException;
    VacancyDTO toDTO(Vacancy entity);
    Vacancy fromDTO(VacancyDTO dto);
    List<String> autocomplete(String text, int count);
}
