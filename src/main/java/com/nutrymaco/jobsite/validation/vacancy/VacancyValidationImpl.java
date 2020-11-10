package com.nutrymaco.jobsite.validation.vacancy;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.exception.validation.VacancyValidationException;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.vacancy.VacancyRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VacancyValidationImpl implements VacancyValidation {
    @Autowired
    CityRepository cityRepository;

    @Autowired
    WorkScheduleRepository scheduleRepository;

    @Autowired
    VacancyRepository vacancyRepository;

    @Override
    public void validate(VacancyDTO dto) throws VacancyValidationException {

        if (vacancyRepository.findFirstByTitleAndDescriptionAndCityId(
                dto.getTitle(), dto.getDescription(), dto.getCityId()
        ) != null) {
            throw new VacancyValidationException("vacancy with that title, description and city exists");
        }
    }
}
