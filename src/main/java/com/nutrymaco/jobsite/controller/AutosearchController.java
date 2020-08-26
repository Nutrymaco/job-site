package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.response.AutosearchResponse;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.security.JWTAuthenticationManager;
import com.nutrymaco.jobsite.service.autosearch.AutosearchService;
import com.nutrymaco.jobsite.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AutosearchController {

    @Autowired
    JWTAuthenticationManager jwtAuthenticationManager;

    @Autowired
    AutosearchService autosearchService;

    @Autowired
    UserService userService;

    @GetMapping("/users/{userId}/autosearches")
    ResponseEntity<?> all(HttpServletRequest request, @PathVariable String userId) {
        log.info(String.format("request to get autosearches by userid=%s", userId));
        jwtAuthenticationManager.authenticate(request).checkId(userId);

        Function<Integer, List<String>> getVacanciesByAutosearchId =
                (autosearchId) ->  autosearchService.getNewVacanciesIdListForAutosearchAndForUser(autosearchId, userId);

        AutosearchResponse autosearchResponse = new AutosearchResponse();
        List<Autosearch> autosearchList = autosearchService.getAutosearchesByUserId(userId);

        for (Autosearch a : autosearchList) {
            autosearchResponse.autosearchByUserAdder()
                    .setAutosearch(a)
                    .setNewVacanciesIdList(getVacanciesByAutosearchId.apply(a.getId()))
                    .add();
        }

        return ResponseEntity.ok(autosearchResponse);
    }



}
