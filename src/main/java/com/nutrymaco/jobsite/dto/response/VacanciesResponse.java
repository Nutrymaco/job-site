package com.nutrymaco.jobsite.dto.response;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.entity.Vacancy;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VacanciesResponse {
    private List<VacancyDTO> vacancies;

    private VacanciesResponse() {}

    public static VacanciesResponse of(List<VacancyDTO> vacancies) {
        VacanciesResponse response = new VacanciesResponse();
        response.setVacancies(vacancies);
        return response;
    }
}
