package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.UserDTO;
import com.nutrymaco.jobsite.entity.User;
import com.nutrymaco.jobsite.exception.found.UserNotFoundException;
import com.nutrymaco.jobsite.security.JWTTokenManager;
import com.nutrymaco.jobsite.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    @Autowired
    private JWTTokenManager jwtTokenManager;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> registryUser(HttpServletRequest request) throws UserNotFoundException {
        log.info("request to registry user");
        UserDTO user = jwtTokenManager.checkToken(request).generateUser();
        UserDTO registeredUser = userService.save(user);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{id}/secret")
    public String getUserSecret(HttpServletRequest request, @PathVariable String id) {
        log.info(String.format("request to %s/secret", id));
        try {
            jwtTokenManager.checkToken(request)
                    .findUser()
                    .checkId(id);
        } catch (Exception e) {
            return "not found";
        }
        return "secret";
    }
}
