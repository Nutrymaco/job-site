package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.dto.request.VacancyFilterRequest;
import com.nutrymaco.jobsite.dto.response.AutosearchResponse;
import com.nutrymaco.jobsite.dto.response.VacanciesResponse;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.security.JWTTokenManager;
import com.nutrymaco.jobsite.service.autosearch.AutosearchService;
import com.nutrymaco.jobsite.service.user.UserService;
import com.nutrymaco.jobsite.service.vacancy.VacancyFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AutosearchController {

    @Autowired
    JWTTokenManager jwtTokenManager;

    @Autowired
    AutosearchService autosearchService;

    @Autowired
    UserService userService;

    @Autowired
    VacancyFilterService filterService;


    @GetMapping("/users/{userId}/autosearches")
    ResponseEntity<?> all(HttpServletRequest request, @PathVariable String userId) {
        log.info(String.format("request to get autosearches by userid=%s", userId));
//        try {
//            jwtTokenManager.checkToken(request)
//                            .findUser()
//                            .checkId(userId);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }

        Function<Integer, List<Vacancy>> getVacanciesByAutosearchId =
                (autosearchId) ->  autosearchService.getNewVacanciesForAutosearchAndUser(autosearchId, userId);

        AutosearchResponse autosearchResponse = new AutosearchResponse();
        List<Autosearch> autosearchList = autosearchService.getAutosearchesByUserId(userId);

        for (Autosearch a : autosearchList) {
            autosearchResponse.autosearchByUserAdder()
                    .setAutosearch(a)
                    .setcountOfNewVacancies(getVacanciesByAutosearchId.apply(a.getId()).size())
                    .add();
        }

        return ResponseEntity.ok(autosearchResponse);
    }

    @PostMapping("/users/{userId}/autosearches")
    ResponseEntity<?> createAutosearch(HttpServletRequest request,
                                       @RequestBody VacancyFilterRequest filter,
                                       @PathVariable String userId) throws Exception {
        Autosearch autosearch;
//        try {
//            jwtTokenManager.checkToken(request)
//                    .findUser()
//                    .checkId(userId);
//        } catch (Exception e) {
//            return ResponseEntity.status(403).build();
//        }
        System.out.println(filter);
//        try {
            autosearch = autosearchService.addAutosearch(userId, filterService.fromVacancyFilterRequest(filter));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
        return ResponseEntity.ok(autosearch);
    }

    @GetMapping("/autosearches/{autosearchId}")
    ResponseEntity<?> getAutosearch(@PathVariable  int autosearchId) {
        Optional<Autosearch> autosearch = autosearchService.getAutosearchById(autosearchId);
        return ResponseEntity.of(autosearch);
    }

    @GetMapping("/users/{userId}/autosearches/{autosearchId}/vacancies")
    ResponseEntity<?> getVacanciesByAutosearch(@PathVariable String userId,
                                                @PathVariable int autosearchId) {
        List<Vacancy> vacancies = autosearchService.getNewVacanciesForAutosearchAndUser(autosearchId, userId);
        return ResponseEntity.ok(VacanciesResponse.of(vacancies));
    }

    @GetMapping("/autosearches/update")
    ResponseEntity<?> updateAllAutosearches() {
        autosearchService.updateAllAutosearches();
        return ResponseEntity.ok().build();
    }


}
