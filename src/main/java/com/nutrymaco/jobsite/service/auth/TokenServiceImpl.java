package com.nutrymaco.jobsite.service.auth;

import com.nutrymaco.jobsite.exception.found.TokenNotFoundException;
import com.nutrymaco.jobsite.util.Random;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TokenServiceImpl implements TokenService {

    private final static int TOKEN_SIZE = 32;

    private ValueOperations<String, String> valueOperations;

    @Override
    public String createTokenForId(String id) {
        String token = Random.getString(TOKEN_SIZE);
        valueOperations.set("token:" + token, id);
        return token;
    }

    @Override
    public boolean checkTokenForId(String id, String token) {
        Optional<String> foundId = Optional.ofNullable(valueOperations.get("token:" + token));
        return foundId.isPresent();
    }

    @Override
    public String getUserIdByToken(String token) throws TokenNotFoundException {
        Optional<String> id = Optional.ofNullable(valueOperations.get("token:" + token));
        if (id.isEmpty()) {
            throw new TokenNotFoundException();
        }
        return id.get();
    }
}
