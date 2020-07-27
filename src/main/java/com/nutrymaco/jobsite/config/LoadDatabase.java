package com.nutrymaco.jobsite.config;

import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.Country;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(CityRepository cityRepository, CountryRepository countryRepository) {
        return args -> {
            System.out.println("cities added to postgres (:");
            Country russia = new Country();
            russia.setName("russia");

            russia = countryRepository.save(russia);

            City moscow = new City();
            moscow.setCountry(russia);
            moscow.setName("moscow");

            City tyumen = new City();
            tyumen.setCountry(russia);
            tyumen.setName("tyumen");

            cityRepository.save(tyumen);
            cityRepository.save(moscow);
        };
    }
}
