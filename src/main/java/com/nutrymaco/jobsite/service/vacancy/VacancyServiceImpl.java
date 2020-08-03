package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.exception.validation.FilterValidationException;
import com.nutrymaco.jobsite.exception.validation.VacancyValidationException;
import com.nutrymaco.jobsite.exception.validation.ValidationException;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.CountryRepository;
import com.nutrymaco.jobsite.repository.VacancyRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import com.nutrymaco.jobsite.validation.vacancy.VacancyFilterValidation;
import com.nutrymaco.jobsite.validation.vacancy.VacancyValidation;

import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nutrymaco.jobsite.adapter.UrlParamsToElasticQuery.getQueryFromVacancyParams;
import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class VacancyServiceImpl implements VacancyService {

    @Autowired
    VacancyRepository vacancyRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    WorkScheduleRepository scheduleRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    ElasticsearchRestTemplate restTemplate;

    @Autowired
    VacancyValidation vacancyValidation;

    @Autowired
    RestHighLevelClient client;

    @Override
    public Vacancy save(VacancyDTO vacancyDTO) throws ValidationException {
        vacancyValidation.validate(vacancyDTO);
        if (vacancyDTO.getCity() != null) {
            vacancyDTO.setCityId(cityRepository.findByName(vacancyDTO.getCity()).getId());
        }
        if (vacancyDTO.getWorkSchedule() != null) {
            vacancyDTO.setWorkScheduleId(scheduleRepository.findByName(vacancyDTO.getWorkSchedule()).getId());
        }

        return vacancyRepository.save(fromDTO(vacancyDTO));
    }

    @Override
    public void removeById(String id) {
        vacancyRepository.deleteById(id);
    }

    @Override
    public void removeAll() {
        vacancyRepository.deleteAll();
    }

    @Override
    public Optional<Vacancy> load(String id) {
        return vacancyRepository.findById(id);
    }

    @Override
    public List<Vacancy> loadValues(MultiValueMap<String, String> map) throws FilterValidationException {
        if (map == null){
            List<Vacancy> vacancies = new ArrayList<>((int) vacancyRepository.count());
            for (Vacancy v : vacancyRepository.findAll()) {
                vacancies.add(v);
            }
            return vacancies;
        }

        VacancyFilterValidation.validateVacancyFilter(map);

        List<String> cities = map.get("city");
        if (cities != null) {
            for (String name : cities) {
                map.set("cityId", cityRepository.findByName(name).getId().toString());
            }
        }

        List<String> schedules = map.get("schedule");
        if (schedules != null) {
            for (String name : schedules) {
                map.set("scheduleId", scheduleRepository.findByName(name).getId().toString());
            }
        }

        Query query = getQueryFromVacancyParams(map);

        System.out.println(restTemplate.search(query, Vacancy.class));
        return restTemplate.search(query, Vacancy.class).stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @Override
    public VacancyDTO toDTO(Vacancy entity) {
        return VacancyDTO.builder()
                .title(entity.getTitle())
                .company(entity.getCompany())
                .description(entity.getDescription())
                .city(entity.getCity())
                .cityId(entity.getCityId())
                .workSchedule(entity.getWorkSchedule())
                .workScheduleId(entity.getWorkScheduleId())
                .experienceFrom(entity.getExperienceFrom())
                .experienceTo(entity.getExperienceTo())
                .salaryFrom(entity.getSalaryFrom())
                .salaryTo(entity.getSalaryTo())
                .url(entity.getUrl())
                .date(entity.getDate())
                .build();
    }

    @Override
    public Vacancy fromDTO(VacancyDTO vacancyDTO) {
        return Vacancy.builder()
                .title(vacancyDTO.getTitle())
                .company(vacancyDTO.getCompany())
                .description(vacancyDTO.getDescription())
                .city(vacancyDTO.getCity())
                .cityId(vacancyDTO.getCityId())
                .experienceFrom(vacancyDTO.getExperienceFrom())
                .experienceTo(vacancyDTO.getExperienceTo())
                .workSchedule(vacancyDTO.getWorkSchedule())
                .workScheduleId(vacancyDTO.getWorkScheduleId())
                .salaryFrom(vacancyDTO.getSalaryFrom())
                .salaryTo(vacancyDTO.getSalaryTo())
                .url(vacancyDTO.getUrl())
                .date(vacancyDTO.getDate())
                .build();
    }

    @Override
    public List<String> autocomplete(String text, int count) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhrasePrefixQuery("title", text))
                .build();


//        System.out.println(query.getQuery());
//        System.out.println(restTemplate.search(query, Vacancy.class).getSearchHits());
        return restTemplate.search(query, Vacancy.class)
                .stream()
                .map(SearchHit::getContent)
                .map(Vacancy::getTitle)
                .collect(Collectors.toList());
    }


}
