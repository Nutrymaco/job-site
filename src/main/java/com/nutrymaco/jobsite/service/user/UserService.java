package com.nutrymaco.jobsite.service.user;

import com.nutrymaco.jobsite.dto.UserDTO;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registry(User user);
    Optional<User> getById(String id);
    User save(User user);
}
