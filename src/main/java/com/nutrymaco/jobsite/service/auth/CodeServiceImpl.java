package com.nutrymaco.jobsite.service.auth;

import com.nutrymaco.jobsite.dto.BaseUserDTO;
import com.nutrymaco.jobsite.exception.found.UserNotFoundException;
import com.nutrymaco.jobsite.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


@Service
public class CodeServiceImpl implements CodeService {

    private final static int CODE_SIZE = 6;

    private final static String CODE_PREFIX = "code:";

    private ValueOperations<String, String> valueOperations;



    public String createCodeForUser(BaseUserDTO user) {
        String code = Random.getString(CODE_SIZE);
        valueOperations.set(CODE_PREFIX + code, user.getId());
        return code;
    }

    @Override
    public void sendCodeForUser(BaseUserDTO user) {
        String code = createCodeForUser(user);
        /*
            here will be logic to send code via email or other way
         */
    }

    @Override
    public String getUserIdByCode(String code) throws UserNotFoundException {
        String id = valueOperations.get(CODE_PREFIX + code);
        if (id == null) {
            throw new UserNotFoundException();
        }
        return id;
    }

}
