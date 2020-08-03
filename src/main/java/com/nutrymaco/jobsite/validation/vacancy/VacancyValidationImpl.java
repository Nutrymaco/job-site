package com.nutrymaco.jobsite.validation.vacancy;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.exception.validation.VacancyValidationException;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.VacancyRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VacancyValidationImpl implements VacancyValidation {
    @Autowired
    CityRepository cityRepository;

    @Autowired
    WorkScheduleRepository scheduleRepository;

    @Autowired
    VacancyRepository vacancyRepository;

    @Override
    public void validate(VacancyDTO entity) throws VacancyValidationException {
        if (entity.getCity() != null && cityRepository.findByName(entity.getCity()) == null) {
            throw new VacancyValidationException(String.format("city %s not found", entity.getCity()));
        }
        if (entity.getWorkSchedule() != null && scheduleRepository.findByName(entity.getWorkSchedule()) == null) {
            throw new VacancyValidationException(String.format("schedule %s not found", entity.getWorkSchedule()));
        }

        if (vacancyRepository.findByTitleAndDescriptionAndCity(
                entity.getTitle(), entity.getDescription(), entity.getCity()
        ) != null) {
            throw new VacancyValidationException("vacancy with that title, description and city exists");
        }
    }
}
