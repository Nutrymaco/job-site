package com.nutrymaco.jobsite.validation;

import com.nutrymaco.jobsite.exception.validation.VacancyValidationException;
import com.nutrymaco.jobsite.exception.validation.ValidationException;

public interface Validation<DTO> {
    void validate(DTO dto) throws ValidationException;
}
