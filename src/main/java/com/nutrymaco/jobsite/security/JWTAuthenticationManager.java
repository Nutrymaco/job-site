package com.nutrymaco.jobsite.security;

import com.nutrymaco.jobsite.entity.User;
import com.nutrymaco.jobsite.service.user.UserService;
import com.nutrymaco.jobsite.util.jwt.GoogleJWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

@Component
public class JWTAuthenticationManager{
    @Autowired
    UserService userService;

    String token;
    GoogleJWTUtil googleJWTUtil;
    User user;

    public IdChecker authenticate(HttpServletRequest request) {
        token = getTokenFromRequest(request);
        checkToken();
        createUser();
        return new IdChecker(user);
    }

    private void createUser() {
        user = new User();
        user.setId(googleJWTUtil.getSub());
        user.setName(googleJWTUtil.getName());
        user.setSurname(googleJWTUtil.getSurname());
    }

    private void findUser() throws Exception {
        user = userService.getById(googleJWTUtil.getSub())
                .orElseThrow(() -> new Exception(String.format("user with id = %s not found", googleJWTUtil.getSub())));
    }

    //todo: test this method
    private String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }

    private void checkToken() {
        try {
            System.out.println("in check");
            googleJWTUtil = new GoogleJWTUtil(token);
            System.out.println(googleJWTUtil);
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalStateException(String.format("for token : %s", token));
        }

    }

    public static class IdChecker {
        User user;
        public IdChecker(User user) {
            this.user = user;
        }

        public IdChecker checkId(String otherId) {
            if (!Objects.equals(user.getId(), otherId)) {
                throw new IllegalStateException(String.format("id1 = %s, id2 = %s", user, otherId));
            }

            return this;
        }

        public User getUser() {
            return user;
        }
    }
}
