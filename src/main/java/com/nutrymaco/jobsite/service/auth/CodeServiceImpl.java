package com.nutrymaco.jobsite.service.auth;

import com.nutrymaco.jobsite.entity.BaseUser;
import com.nutrymaco.jobsite.exception.found.UserNotFoundException;
import com.nutrymaco.jobsite.util.Random;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService {

    private final static int CODE_SIZE = 6;

    private ValueOperations<String, String> valueOperations;



    public String createCodeForUser(BaseUser user) {
        String code = Random.getString(CODE_SIZE);
        valueOperations.set("code:" + code, user.getId());
        return code;
    }

    @Override
    public void sendCodeForUser(BaseUser user) {
        String code = createCodeForUser(user);
        /*
            here will be logic to send code via email or other way
         */
    }

    @Override
    public String getUserIdByCode(String code) throws UserNotFoundException {
        String id = valueOperations.get("code:" + code);
        if (id == null) {
            throw new UserNotFoundException();
        }
        return id;
    }

}
