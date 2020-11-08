package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.request.CodeRequest;
import com.nutrymaco.jobsite.dto.request.EmailRequest;
import com.nutrymaco.jobsite.dto.response.TokenResponse;
import com.nutrymaco.jobsite.entity.BaseUser;
import com.nutrymaco.jobsite.exception.found.EmployeeNotFoundException;
import com.nutrymaco.jobsite.exception.found.UserNotFoundException;
import com.nutrymaco.jobsite.service.auth.CodeService;
import com.nutrymaco.jobsite.service.auth.TokenService;
import com.nutrymaco.jobsite.service.company.HREmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthController {

    @Autowired
    private CodeService codeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HREmployeeService employeeService;

    @PostMapping("/generateCode")
    public ResponseEntity<?> generateCode(EmailRequest request) throws EmployeeNotFoundException {
        BaseUser user = employeeService.getByEmail(request.getEmail());
        codeService.sendCodeForUser(user);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/getToken")
    public ResponseEntity<?> getToken(CodeRequest request) throws UserNotFoundException {
        String id = codeService.getUserIdByCode(request.getCode());
        String token = tokenService.createTokenForId(id);
        return ResponseEntity
                .status(201)
                .body(new TokenResponse(token));
    }

}
