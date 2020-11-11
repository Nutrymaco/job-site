package com.nutrymaco.jobsite.mapping;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrymaco.jobsite.dto.Currency;
import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.service.vacancy.VacancyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VacancyMappingTest {

    @Autowired
    VacancyService vacancyService;

    ObjectMapper mapper = new ObjectMapper();

    String title = "junior java";

    String description = "maven spring java maven";

    String company = "javacom";

    Integer experienceFrom = 0;

    Integer experienceTo = 1;

    Integer salaryFrom = 20_000;

    Integer salaryTo = 40_000;

    Currency currency = Currency.RUB;

    Integer cityId = 1;

    Integer workScheduleId = 1;

    String url = "hhr.u/vacancies/qregeqrge";

    Date date = Date.from(Instant.now());

    Vacancy vacancy;


    @Before
    public void initVacancy() {
        vacancy = Vacancy.builder()
                .title(title)
                .description(description)
                .company(company)
                .experienceFrom(experienceFrom)
                .experienceTo(experienceTo)
                .salaryFrom(salaryFrom)
                .salaryTo(salaryTo)
                .currency(currency)
                .cityId(cityId)
                .workScheduleId(workScheduleId)
                .url(url)
                .date(date)
                .build();
    }

    @Test
    public void test() throws JsonProcessingException {
        VacancyDTO toDTO = vacancyService.toDTO(vacancy);
        Vacancy fromDTO = vacancyService.fromDTO(toDTO);

        String expectedVacancy = mapper.writeValueAsString(vacancy);
        String actualVacancy = mapper.writeValueAsString(fromDTO);

        assertEquals(expectedVacancy, actualVacancy);
    }

}
