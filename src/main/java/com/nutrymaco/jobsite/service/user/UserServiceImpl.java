package com.nutrymaco.jobsite.service.user;


import com.nutrymaco.jobsite.dto.UserDTO;
import com.nutrymaco.jobsite.entity.User;
import com.nutrymaco.jobsite.exception.found.UserNotFoundException;
import com.nutrymaco.jobsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Override
    public UserDTO getById(String id) throws UserNotFoundException {
        return userRepository
                .findById(id)
                .map(this::toDTO)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserDTO save(UserDTO dto) throws UserNotFoundException {
        return toDTO(userRepository.save(fromDTO(dto)));
    }

    @Override
    public User fromDTO(UserDTO dto) throws UserNotFoundException {
        Optional<User> existedUser = userRepository.findById(dto.getId());
        if (existedUser.isEmpty()) {
            User user = new User();
            user.setId(dto.getId());
            user.setEmail(dto.getEmail());
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            return user;
        } else {
            User user = existedUser.get();
            if (dto.getFirstName() != null)
                user.setFirstName(dto.getFirstName());
            if (dto.getLastName() != null)
                user.setLastName(dto.getLastName());
            if (dto.getEmail() != null)
                user.setEmail(dto.getEmail());
            return user;
        }
    }

    @Override
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
