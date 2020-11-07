package com.nutrymaco.jobsite.service.auth;

import com.nutrymaco.jobsite.exception.found.TokenNotFoundException;

public interface TokenService {
    String createTokenForId(String id);
    boolean checkTokenForId(String id, String token) throws TokenNotFoundException;
    String getUserIdByToken(String token) throws TokenNotFoundException;
}
