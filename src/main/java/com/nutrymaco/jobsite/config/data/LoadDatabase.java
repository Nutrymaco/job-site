package com.nutrymaco.jobsite.config;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.Country;
import com.nutrymaco.jobsite.entity.User;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.CountryRepository;
import com.nutrymaco.jobsite.repository.UserRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import com.nutrymaco.jobsite.service.autosearch.AutosearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(CityRepository cityRepository,
                                   CountryRepository countryRepository,
                                   WorkScheduleRepository scheduleRepository,
                                   RestHighLevelClient client,
                                   UserRepository userRepository,
                                   AutosearchService autosearchService) {
        return args -> {
//            try {
//                client.indices().delete(new DeleteIndexRequest().indices("site"), RequestOptions.DEFAULT);
//            } catch (Exception e) {
//                System.out.println("cant delete");
//            }
//            CreateIndexRequest request = new CreateIndexRequest("site");
//            Settings.Builder settingsBuilder =
//                    Settings.builder()
//                            .loadFromSource(loadFromFile("/settings/settings.json"), XContentType.JSON);
//
//            request.settings(settingsBuilder);
//            request.mapping(loadFromFile("/settings/mappings.json"), XContentType.JSON);
//            System.out.println(client.indices().create(request, RequestOptions.DEFAULT).index());

            Country russia = new Country();
            russia.setName("russia");

            russia = countryRepository.save(russia);

            City moscow = new City();
            moscow.setCountry(russia);
            moscow.setName("Москва");

            City tyumen = new City();
            tyumen.setCountry(russia);
            tyumen.setName("Тюмень");

            cityRepository.save(tyumen);
            cityRepository.save(moscow);

            System.out.println("cities added to postgres (:");
            WorkSchedule workSchedule = new WorkSchedule();
            workSchedule.setName("FULL");

            WorkSchedule workSchedule1 = new WorkSchedule();
            workSchedule1.setName("PART");

            WorkSchedule workSchedule2 = new WorkSchedule();
            workSchedule2.setName("FLEX");

            WorkSchedule workSchedule3 = new WorkSchedule();
            workSchedule3.setName("REMOTE");

            WorkSchedule workSchedule4 = new WorkSchedule();
            workSchedule4.setName("OTHER");
//
            scheduleRepository.save(workSchedule);
            scheduleRepository.save(workSchedule1);
            scheduleRepository.save(workSchedule2);
            scheduleRepository.save(workSchedule3);
            scheduleRepository.save(workSchedule4);
//
            User user = new User();
            user.setId("1");
            user.setName("Efim");
            user.setViewedVacanciesIds(new ArrayList<>());
            userRepository.save(user);

            VacancyFilter vacancyFilter = VacancyFilter.builder()
                    .text("java senior")
                    .experience(6)
                    .salary(100_000)
                    .cities(List.of(cityRepository.findByName("Москва")))
                    .workSchedules(List.of(scheduleRepository.findByName("FULL")))
                    .build();
            autosearchService.addAutosearch("1", vacancyFilter);
            
            VacancyFilter vacancyFilter2 = VacancyFilter.builder()
                    .text("java junior")
                    .experience(0)
                    .salary(0)
                    .cities(List.of(cityRepository.findByName("Москва"), cityRepository.findByName("Тюмень")))
                    .workSchedules(List.of(scheduleRepository.findByName("FLEX")))
                    .build();
            autosearchService.addAutosearch("1", vacancyFilter2);

            
        };
    }

    private void addCities() {

    }

    protected String loadFromFile(String fileName) throws IllegalStateException {
        StringBuilder buffer = new StringBuilder(2048);
        try {
            InputStream is = getClass().getResourceAsStream(fileName);
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
            while (reader.ready()) {
                buffer.append(reader.readLine());
                buffer.append(' ');
            }
        } catch (Exception e) {
            throw new IllegalStateException("couldn't load file " + fileName, e);
        }
        return buffer.toString();
    }
}
