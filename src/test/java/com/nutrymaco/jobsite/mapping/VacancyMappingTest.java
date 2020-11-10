package com.nutrymaco.jobsite.mapping;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrymaco.jobsite.dto.Currency;
import com.nutrymaco.jobsite.service.vacancy.VacancyService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VacancyMappingTest {

    @Autowired
    VacancyService vacancyService;

    ObjectMapper mapper = new ObjectMapper();

    String id = "qregeqrge";

    String title = "junior java";

    String description = "maven spring java maven";

    String company = "javacom";

    Integer experienceFrom = 0;

    Integer experienceTo = 1;

    Integer salaryFrom = 20_000;

    Integer salaryTo = 40_000;

    Currency currency = Currency.RUB;

    String city = "Москва";

    Integer cityId = 1;

    String workSchedule = "FLEX";

    Integer workScheduleId = 1;

    String url = "hhr.u/vacancies/qregeqrge";

    Date date = Date.from(Instant.now());

}
