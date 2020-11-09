package com.nutrymaco.jobsite.service.auth;

import com.nutrymaco.jobsite.dto.BaseUserDTO;
import com.nutrymaco.jobsite.entity.BaseUser;
import com.nutrymaco.jobsite.exception.found.UserNotFoundException;

public interface CodeService {
    void sendCodeForUser(BaseUserDTO user);

    String getUserIdByCode(String code) throws UserNotFoundException;
}
