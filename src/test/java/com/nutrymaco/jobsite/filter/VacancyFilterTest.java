package com.nutrymaco.jobsite.filter;


import com.nutrymaco.jobsite.controller.VacancyController;
import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.dto.response.VacanciesResponse;
import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.Country;
import com.nutrymaco.jobsite.exception.validation.ValidationException;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.CountryRepository;
import com.nutrymaco.jobsite.service.vacancy.VacancyService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VacancyFilterTest {

    @Autowired
    VacancyController controller;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    VacancyService vacancyService;

    private final VacancyDTO bigSalary, avgSalary, smallSalary,
            expFrom0To1, expFrom1to3, expFrom6;



    {
        bigSalary = VacancyDTO.builder()
                .title("Вакансия с большой зарплатой")
                .description("JAVA JAVA SPRING сениор DEVELOPER")
                .cityId(1)
                .salaryFrom(300_000)
                .salaryTo(500_000)
                .build();
        avgSalary = VacancyDTO.builder()
                .title("Вакансия со среднее зарплатой")
                .cityId(2)
                .salaryFrom(150_000)
                .salaryTo(200_000)
                .build();
        smallSalary = VacancyDTO.builder()
                .title("Вакансия с маленькой зарплатой")
                .cityId(1)
                .salaryTo(60_000)
                .build();
        expFrom0To1 = VacancyDTO.builder()
                .title("Вакансия с небольшим опытом")
                .experienceFrom(0)
                .experienceTo(1)
                .cityId(1)
                .build();
        expFrom1to3 = VacancyDTO.builder()
                .title("Вакансия со средним опытом")
                .experienceFrom(1)
                .experienceTo(3)
                .cityId(2)
                .build();
        expFrom6 = VacancyDTO.builder()
                .title("Вакансия с большим опытом")
                .experienceFrom(6)
                .cityId(2)
                .build();
    }
    private final List<VacancyDTO> testVacancies =
            List.of(bigSalary, avgSalary, smallSalary,
                    expFrom0To1, expFrom1to3, expFrom6);


    @Before
    public void initData() {
        countryRepository.save(new Country(1, "Россия"));
        Country russia = countryRepository.findById(1).get();
        cityRepository.save(new City(1, "Тюмень", russia));
        cityRepository.save(new City(2, "Москва", russia));

        vacancyService.removeAll();

        testVacancies
                .forEach(v -> {
                    try {
                        controller.createVacancy(v);
                    } catch (ValidationException e) {
                        fail("не удалось загрузить вакансию из-за ошибки валидации");
                    }
                });
    }

    @Test
    public void testGetAllVacancies() throws ValidationException {
        ResponseEntity<VacanciesResponse> vacancies = controller.getVacancies(VacancyFilter.empty());
        assertEquals(
                Objects.requireNonNull(vacancies.getBody()).getVacancies().size(),
                testVacancies.size());
    }

    @Test
    public void testNoSalary() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(VacancyFilter.empty()).getBody().getVacancies();

        assertTrue(vacancies.contains(smallSalary));
        assertTrue(vacancies.contains(avgSalary));
        assertTrue(vacancies.contains(bigSalary));
    }

    @Test
    public void testSmallSalary() throws ValidationException {
        final VacancyFilter filters = VacancyFilter.builder()
                                                    .salary(1000)
                                                    .build();

        List<VacancyDTO> vacancies = controller.getVacancies(filters).getBody().getVacancies();

        assertTrue(vacancies.contains(smallSalary));
        assertTrue(vacancies.contains(avgSalary));
        assertTrue(vacancies.contains(bigSalary));
    }

    @Test
    public void testAvgSalary() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(
                VacancyFilter.builder()
                        .salary(100_000)
                        .build()).getBody().getVacancies();

        assertFalse(vacancies.contains(smallSalary));
        assertTrue(vacancies.contains(avgSalary));
        assertTrue(vacancies.contains(bigSalary));
    }

    @Test
    public void testBigSalary() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(
                VacancyFilter.builder()
                        .salary(1_000_000)
                        .build()
        ).getBody().getVacancies();

        assertTrue(vacancies.isEmpty());
    }


    @Test
    public void testNoExp() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(VacancyFilter.empty()).getBody().getVacancies();

        assertTrue(vacancies.contains(expFrom0To1));
        assertTrue(vacancies.contains(expFrom1to3));
        assertTrue(vacancies.contains(expFrom6));
    }

    @Test
    public void testSmallExp() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(
                VacancyFilter.builder()
                        .experience(0)
                        .build()
        ).getBody().getVacancies();

        assertTrue(vacancies.contains(expFrom0To1));
        assertFalse(vacancies.contains(expFrom1to3));
        assertFalse(vacancies.contains(expFrom6));
    }

    @Test
    public void testAvgExp() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(
                VacancyFilter.builder()
                        .experience(2)
                        .build()
        ).getBody().getVacancies();

        assertFalse(vacancies.contains(expFrom0To1));
        assertTrue(vacancies.contains(expFrom1to3));
        assertFalse(vacancies.contains(expFrom6));
    }

    @Test
    public void testBigExp() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(
                VacancyFilter.builder()
                        .experience(6)
                        .build()
        ).getBody().getVacancies();

        assertFalse(vacancies.contains(expFrom0To1));
        assertFalse(vacancies.contains(expFrom1to3));
        assertTrue(vacancies.contains(expFrom6));
    }

    @Test
    public void testCityFilter() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(
                VacancyFilter.builder()
                        .cityIdList(List.of(1))
                        .build()
        ).getBody().getVacancies();

        List<VacancyDTO> expectedVacancies =
                testVacancies.stream()
                        .filter(v -> v.getCityId() == 1)
                        .collect(Collectors.toList());



        assertTrue(expectedVacancies.containsAll(vacancies));
        assertEquals(expectedVacancies.size(), vacancies.size());
    }

    @Test
    public void testWorkScheduleFilter() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(
                VacancyFilter.builder()
                        .workScheduleIdList(List.of(3))
                        .build()
        ).getBody().getVacancies();

        List<VacancyDTO> expectedVacancies = testVacancies.stream()
                .filter(v -> v.getWorkScheduleId() != null && v.getWorkScheduleId() == 3)
                .collect(Collectors.toList());

        assertEquals(expectedVacancies, vacancies);
    }

    @Test
    public void testSmallSalaryCity1() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(
                VacancyFilter.builder()
                        .salary(1000)
                        .cityIdList(List.of(1))
                        .build()
        ).getBody().getVacancies();

        assertTrue(vacancies.contains(smallSalary));
        assertFalse(vacancies.contains(avgSalary));
        assertTrue(vacancies.contains(bigSalary));
    }

    @Test
    public void testBigExperienceCity1() throws ValidationException {
        List<VacancyDTO> vacancies = controller.getVacancies(
                VacancyFilter.builder()
                        .experience(6)
                        .cityIdList(List.of(1))
                        .build()
        ).getBody().getVacancies();

        assertFalse(vacancies.contains(expFrom0To1));
        assertFalse(vacancies.contains(expFrom1to3));
        assertFalse(vacancies.contains(expFrom6));
    }
}
