package com.nutrymaco.jobsite.assembler;

import com.nutrymaco.jobsite.controller.VacancyController;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.exception.validation.ValidationException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class VacancyAssembler implements RepresentationModelAssembler<Vacancy, EntityModel<Vacancy>> {

    @Override
    public EntityModel<Vacancy> toModel(Vacancy entity) {
        try {
            return EntityModel.of(entity,
                    WebMvcLinkBuilder.linkTo(methodOn(VacancyController.class).oneVacancy(entity.getId())).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(methodOn(VacancyController.class).allVacancies(null)).withRel("vacancies")
            );
        } catch (ValidationException e) {
            // never will be here
            e.printStackTrace();
            return EntityModel.of(entity,
                    WebMvcLinkBuilder.linkTo(methodOn(VacancyController.class).oneVacancy(entity.getId())).withSelfRel());
        }
    }
}
