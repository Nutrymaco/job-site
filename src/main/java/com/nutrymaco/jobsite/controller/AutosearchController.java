package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.response.AutosearchResponse;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.security.JWTTokenManager;
import com.nutrymaco.jobsite.service.autosearch.AutosearchService;
import com.nutrymaco.jobsite.service.user.UserService;
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

    @GetMapping("/users/{userId}/autosearches")
    ResponseEntity<?> all(HttpServletRequest request, @PathVariable String userId) {
        log.info(String.format("request to get autosearches by userid=%s", userId));
        try {
            jwtTokenManager.checkToken(request)
                            .findUser()
                            .checkId(userId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

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

    @PostMapping("/users/{userId}/autosearches")
    ResponseEntity<?> createutosearch(HttpServletRequest request,
                                    @RequestBody MultiValueMap<String, String> filters,
                                    @PathVariable String userId) {
        try {
            jwtTokenManager.checkToken(request)
                    .findUser()
                    .checkId(userId);
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }

        try {
            autosearchService.addAutosearch(userId, filters);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("user not found");
        }

        return ResponseEntity.ok().build();
    }



}
