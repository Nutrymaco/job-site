package com.nutrymaco.jobsite.validation.vacancy;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import org.springframework.stereotype.Component;

@Component
public class VacancyValidationImpl implements VacancyValidation {
    @Override
    public boolean validate(VacancyDTO entity) {
        return true;
    }
}
