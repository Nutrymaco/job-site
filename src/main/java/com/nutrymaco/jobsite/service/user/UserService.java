package com.nutrymaco.jobsite.service.user;

import com.nutrymaco.jobsite.dto.UserDTO;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.entity.User;
import com.nutrymaco.jobsite.exception.found.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO getById(String id) throws UserNotFoundException;
    UserDTO save(UserDTO user) throws UserNotFoundException;
    User fromDTO(UserDTO dto) throws UserNotFoundException;
    UserDTO toDTO(User user);
    void addAutosearch(String userId, Autosearch autosearch) throws UserNotFoundException;
    List<Autosearch> getAutosearchesByUserId(String userId) throws UserNotFoundException;
    List<String> getViewedVacanciesIds(String userId) throws UserNotFoundException;
}
