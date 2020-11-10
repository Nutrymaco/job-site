package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.adapter.AutocompleteDBAdapter;
import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.exception.found.VacancyNotFoundException;
import com.nutrymaco.jobsite.exception.validation.FilterValidationException;
import com.nutrymaco.jobsite.exception.validation.ValidationException;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.CountryRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import com.nutrymaco.jobsite.repository.vacancy.VacancyRepository;
import com.nutrymaco.jobsite.repository.vacancy.VacancyRepositoryCustom;
import com.nutrymaco.jobsite.validation.vacancy.VacancyValidation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Autowired
    VacancyFilterService filterService;

    @Autowired
    VacancyRepositoryCustom customVacancyRepository;

    @Autowired
    AutocompleteDBAdapter autocompleteAdapter;

    @Override
    public Vacancy save(VacancyDTO vacancyDTO) throws ValidationException {
        vacancyValidation.validate(vacancyDTO);
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
    public VacancyDTO getById(String id) throws VacancyNotFoundException {
        return vacancyRepository
                .findById(id)
                .map(this::toDTO)
                .orElseThrow(VacancyNotFoundException::new);
    }

    //todo add validation
    @Override
    public List<VacancyDTO> getVacanciesByFilters(MultiValueMap<String, String> filters) throws FilterValidationException {
        return customVacancyRepository
                .findByFilter(filterService.fromMultiValueMap(filters))
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VacancyDTO> getVacanciesByIdList(List<String> idList) {
        return vacancyRepository.findByIdIn(idList).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VacancyDTO toDTO(Vacancy entity) {
        return VacancyDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .company(entity.getCompany())
                .description(entity.getDescription())
                .cityId(entity.getCityId())
                .workScheduleId(entity.getWorkScheduleId())
                .experienceFrom(entity.getExperienceFrom())
                .experienceTo(entity.getExperienceTo())
                .salaryFrom(entity.getSalaryFrom())
                .salaryTo(entity.getSalaryTo())
                .currency(entity.getCurrency())
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
                .cityId(vacancyDTO.getCityId())
                .experienceFrom(vacancyDTO.getExperienceFrom())
                .experienceTo(vacancyDTO.getExperienceTo())
                .workScheduleId(vacancyDTO.getWorkScheduleId())
                .salaryFrom(vacancyDTO.getSalaryFrom())
                .salaryTo(vacancyDTO.getSalaryTo())
                .currency(vacancyDTO.getCurrency())
                .url(vacancyDTO.getUrl())
                .date(vacancyDTO.getDate())
                .build();
    }

    @Override
    public List<String> autocomplete(String text, int count) {
        return autocompleteAdapter.getWordsByQuery(text);
    }


}
