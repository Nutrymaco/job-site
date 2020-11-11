package com.nutrymaco.jobsite.security;

import com.nutrymaco.jobsite.dto.UserDTO;
import com.nutrymaco.jobsite.service.user.UserService;
import com.nutrymaco.jobsite.util.jwt.GoogleJWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

@Component
public class JWTTokenManager {
    @Autowired
    UserService userService;

    String token;
    GoogleJWTUtil googleJWTUtil;
    UserDTO user;
    String id;
    String name;
    String surname;


    public UserManager checkToken(HttpServletRequest request) {
        token = getTokenFromRequest(request);
        checkToken();
        extractInfoFromToken();
        return new UserManager();
    }

    private void createUser() {
        user = new UserDTO();
        user.setId(googleJWTUtil.getSub());
        user.setFirstName(googleJWTUtil.getName());
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }

    private void checkToken() {
        try {
            System.out.println("in check");
            googleJWTUtil = new GoogleJWTUtil(token);
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalStateException(String.format("for token : %s", token));
        }
    }

    private void extractInfoFromToken() {
        id = googleJWTUtil.getSub();
        name = googleJWTUtil.getName();
        surname = googleJWTUtil.getSurname();
    }

    public class IdChecker {
        public IdChecker checkId(String otherId) {
            if (!Objects.equals(user.getId(), otherId)) {
                throw new IllegalStateException(String.format("id1 = %s, id2 = %s", user, otherId));
            }
            return this;
        }

        public UserDTO getUser() {
            return user;
        }
    }
    public class UserManager {
        public IdChecker findUser() throws Exception {
            user = userService.getById(id);
            return new IdChecker();
        }

        public UserDTO generateUser() {
            createUser();
            return user;
        }
    }

}

