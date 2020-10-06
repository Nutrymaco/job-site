package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.Autocomplete;
import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.dto.filter.Filters;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.exception.validation.ValidationException;
import com.nutrymaco.jobsite.security.JWTTokenManager;
import com.nutrymaco.jobsite.service.vacancy.VacancyAdvancedFiltersServiceImpl;
import com.nutrymaco.jobsite.service.vacancy.VacancyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class VacancyController {
    JWTTokenManager jwtTokenManager = new JWTTokenManager();

    @Autowired
    VacancyService vacancyService;

    @GetMapping("/vacancies")
    public ResponseEntity<List<Vacancy>> allVacancies(@RequestParam(required = false) MultiValueMap<String, String> filters) throws ValidationException {
        log.info(String.format("request to get vacancies by filters : %s", filters));
        List<Vacancy> vacancies = vacancyService.getVacanciesByFilters(filters);
        return ResponseEntity.ok(vacancies);
    }

    @GetMapping("/vacancies/{vacancyId}")
    public ResponseEntity<Vacancy> oneVacancy(@PathVariable String vacancyId) {
        log.info(String.format("request get vacancies by vacancy_id : %s", vacancyId));
        Vacancy vacancy = vacancyService.load(vacancyId)
                .orElseThrow(() -> new RuntimeException("cant find vacancy"));

        return ResponseEntity.ok(vacancy);
    }

    @PostMapping("/vacancies")
    public ResponseEntity<Vacancy> createVacancy(@RequestBody VacancyDTO vacancy) throws ValidationException {
        log.info(String.format("request to add vacancy : %s", vacancy));
        Vacancy created = vacancyService.save(vacancy);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/vacancies")
    public ResponseEntity<?> deleteAllVacancies(HttpServletRequest request) {
        log.info("request to delete all vacancies");
//        try {
//            jwtTokenManager.checkToken(request).findUser().checkId("108283747568494427027");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        vacancyService.removeAll();
        return ResponseEntity.ok().build();
    }
}
