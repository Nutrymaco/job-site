package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
