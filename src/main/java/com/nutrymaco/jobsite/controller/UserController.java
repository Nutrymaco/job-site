package com.nutrymaco.jobsite.controller;


import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableWebSecurity
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @GetMapping("{id}/secret")
    public String getUserSecret(@RequestParam long id,  OAuth2ResourceServerProperties.Jwt jwt) {

        return "secret";
    }
}
