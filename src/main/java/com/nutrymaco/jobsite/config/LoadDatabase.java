package com.nutrymaco.jobsite.config;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.App;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.Country;
import com.nutrymaco.jobsite.entity.User;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.repository.AppRepository;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.CountryRepository;
import com.nutrymaco.jobsite.repository.UserRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import com.nutrymaco.jobsite.service.autosearch.AutosearchService;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentParserUtils;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.index.MappingBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Path;
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
            CreateIndexRequest request = new CreateIndexRequest("site");

            Settings.Builder settingsBuilder =
                    Settings.builder()
                            .loadFromSource(loadFromFile("/settings/settings.json"), XContentType.JSON);

            request.settings(settingsBuilder);
            request.mapping(loadFromFile("/settings/mappings.json"), XContentType.JSON);
            System.out.println(client.indices().create(request, RequestOptions.DEFAULT).index());

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
