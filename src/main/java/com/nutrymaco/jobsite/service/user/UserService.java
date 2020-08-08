package com.nutrymaco.jobsite.service.user;

import com.nutrymaco.jobsite.dto.UserDTO;
import com.nutrymaco.jobsite.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User findByUsername(String username);
    User registry(UserDTO user);
}
