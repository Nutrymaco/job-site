package com.nutrymaco.jobsite.config.data;

import com.nutrymaco.jobsite.entity.User;
import com.nutrymaco.jobsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

//todo add flag to add or not data
@Configuration
public class UserData {

    @Autowired
    UserRepository userRepository;

    @Bean
    ApplicationRunner userInit() {
        return args -> {
            User user = new User();
            user.setId("1");
            user.setName("Efim");
            user.setViewedVacanciesIds(new ArrayList<>());
            userRepository.save(user);
        };
    }

}
