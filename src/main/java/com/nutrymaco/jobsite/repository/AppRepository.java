package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.App;
import org.springframework.data.repository.CrudRepository;

public interface AppRepository extends CrudRepository<App, Integer> {
    App findByName(String name);
}
