package com.nutrymaco.jobsite.service.user;


import com.nutrymaco.jobsite.dto.UserDTO;
import com.nutrymaco.jobsite.entity.User;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User registry(UserDTO user) {
        return null;
    }
}
