package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.repository.AutosearchRepository;
import com.nutrymaco.jobsite.service.autosearch.AutosearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AutosearchController {

    @Autowired
    AutosearchService autosearchService;

    @GetMapping("/autosearches")
    ResponseEntity<?> all() {
        return ResponseEntity.ok(autosearchService.getAll());
    }
}
