package com.nutrymaco.jobsite.config.data;

import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.Country;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.CountryRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class CityData {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountryRepository countryRepository;

    @Bean("countryInit")
    ApplicationRunner countyInit() {
        return args -> {
            countryRepository.save(new Country(1, "Россия"));
        };
    }

    @Bean
    @DependsOn("countryInit")
    ApplicationRunner cityApplicationRunner() {
        return args -> {
            Country russia = countryRepository.findById(1).get();
            cityRepository.save(new City(1, "Тюмень", russia));
            cityRepository.save(new City(2, "Москва", russia));
        };
    }

}
