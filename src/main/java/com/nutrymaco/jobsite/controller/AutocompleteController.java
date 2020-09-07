package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.Autocomplete;
import com.nutrymaco.jobsite.service.vacancy.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AutocompleteController {
    @Autowired
    VacancyService vacancyService;

    @GetMapping("/autocomplete")
    public EntityModel<Autocomplete> getAutocomplete(@RequestParam String text,
                                                     @RequestParam(defaultValue = "10") int count) {
        Autocomplete a = new Autocomplete();
        a.setOptions(vacancyService.autocomplete(text, count));
        return EntityModel.of(a);
    }
}
