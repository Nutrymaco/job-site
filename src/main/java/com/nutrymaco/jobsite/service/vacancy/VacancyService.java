package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.exception.found.VacancyNotFoundException;
import com.nutrymaco.jobsite.exception.validation.FilterValidationException;
import com.nutrymaco.jobsite.exception.validation.ValidationException;
import org.springframework.util.MultiValueMap;

import java.util.List;

@org.springframework.stereotype.Service
public interface VacancyService {
    Vacancy save(VacancyDTO dto) throws ValidationException;
    void removeById(String id);
    void removeAll();
    VacancyDTO getById(String id) throws VacancyNotFoundException;
    List<VacancyDTO> getVacanciesByFilters(MultiValueMap<String, String> filters) throws FilterValidationException;
    List<VacancyDTO> getVacanciesByIdList(List<String> idList);
    VacancyDTO toDTO(Vacancy entity);
    Vacancy fromDTO(VacancyDTO dto);
    List<String> autocomplete(String text, int count);
}
