package com.nutrymaco.jobsite.validation;

public interface Validation<DTO> {
    boolean validate(DTO dto);
}
