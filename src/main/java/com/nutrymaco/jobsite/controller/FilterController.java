package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.filter.Filters;
import com.nutrymaco.jobsite.service.vacancy.VacancyAdvancedFiltersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class FilterController {
    @Autowired
    private VacancyAdvancedFiltersService filtersService;

    @GetMapping("/advanced_filters")
    public EntityModel<Filters> getAdvancedFilters() {
        return EntityModel.of(filtersService.getFilters());
    }

}
