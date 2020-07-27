package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.exception.validation.FilterValidationException;
import com.nutrymaco.jobsite.exception.validation.VacancyValidationException;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.CountryRepository;
import com.nutrymaco.jobsite.repository.VacancyRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import com.nutrymaco.jobsite.validation.vacancy.VacancyFilterValidation;
import com.nutrymaco.jobsite.validation.vacancy.VacancyValidation;

import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nutrymaco.jobsite.adapter.UrlParamsToElasticQuery.getQueryFromVacancyParams;
import static org.elasticsearch.index.query.QueryBuilders.matchPhrasePrefixQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

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
    public Vacancy save(VacancyDTO vacancyDTO) throws VacancyValidationException {
        if (!vacancyValidation.validate(vacancyDTO)) {
            throw new VacancyValidationException();
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

        if (!VacancyFilterValidation.validateVacancyFilter(map)) {
            throw new FilterValidationException();
        }

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

        return restTemplate.search(query, Vacancy.class).stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @Override
    public VacancyDTO toDTO(Vacancy entity) {
        return VacancyDTO.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .city(entity.getCity())
                .cityId(entity.getCityId())
                .workSchedule(entity.getWorkSchedule())
                .workScheduleId(entity.getWorkScheduleId())
                .experienceFrom(entity.getExperienceFrom())
                .experienceTo(entity.getExperienceTo())
                .salaryFrom(entity.getSalaryFrom())
                .salaryTo(entity.getSalaryTo())
                .build();
    }

    @Override
    public Vacancy fromDTO(VacancyDTO vacancyDTO) {
        return Vacancy.builder()
                .title(vacancyDTO.getTitle())
                .description(vacancyDTO.getDescription())
                .city(vacancyDTO.getCity())
                .cityId(vacancyDTO.getCityId())
                .experienceFrom(vacancyDTO.getExperienceFrom())
                .experienceTo(vacancyDTO.getExperienceTo())
                .workSchedule(vacancyDTO.getWorkSchedule())
                .workScheduleId(vacancyDTO.getWorkScheduleId())
                .salaryFrom(vacancyDTO.getSalaryFrom())
                .salaryTo(vacancyDTO.getSalaryTo())
                .build();
    }

    @Override
    public List<String> autocomplete(String text, int count) {
        return restTemplate.search(new NativeSearchQueryBuilder()
                                        .withQuery(matchQuery("title", text))
                                        .build(), Vacancy.class)
                .stream()
                .map(SearchHit::getContent)
                .map(Vacancy::getTitle)
                .collect(Collectors.toList());
    }


}
