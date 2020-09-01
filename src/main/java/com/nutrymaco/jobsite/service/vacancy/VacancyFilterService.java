package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.Vacancy;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

public interface VacancyFilterService {
    VacancyFilter fromMultiValueMap(MultiValueMap<String, String> filters);
    MultiValueMap<String, String> toMultiValueMap(VacancyFilter filter);
}
