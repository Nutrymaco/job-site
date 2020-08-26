package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
}
