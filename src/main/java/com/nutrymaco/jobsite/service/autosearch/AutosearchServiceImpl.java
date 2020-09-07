package com.nutrymaco.jobsite.service.autosearch;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.entity.User;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.exception.validation.FilterValidationException;
import com.nutrymaco.jobsite.repository.AutosearchRepository;
import com.nutrymaco.jobsite.service.user.UserService;
import com.nutrymaco.jobsite.service.vacancy.VacancyFilterService;
import com.nutrymaco.jobsite.service.vacancy.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Service
public class AutosearchServiceImpl implements AutosearchService {

    @Autowired
    VacancyService vacancyService;

    @Autowired
    UserService userService;

    @Autowired
    AutosearchRepository autosearchRepository;

    @Autowired
    VacancyFilterService filterService;


    @Override
    public Optional<Autosearch> getAutosearchById(int id) {
        return autosearchRepository.findById(id);
    }

    @Override
    public List<Autosearch> getAll() {
        return (List<Autosearch>) autosearchRepository.findAll();
    }

    @Override
    public boolean exists(Autosearch autosearch) {
        return exists(autosearch.getFilter());
    }

    public boolean exists(VacancyFilter filter) {
        return getIdByFilter(filter)
                .isPresent();
    }

//    @Override
//    public void addAutosearch(Autosearch autosearch) {
//        if (!exists(autosearch)) {
//            autosearchRepository.save(autosearch);
//        }
//    }

    public OptionalInt getIdByFilter(VacancyFilter filter) {
        return autosearchRepository.findByTextAndExpFromAndExpToAndSalaryFromAndExpToAndCitiesAndWorkSchedules(
                filter.getText(),
                filter.getExperience(),
                filter.getSalary(),
                filter.getCities(), filter.getWorkSchedules()
        );
    }

    @Override
    public Autosearch addAutosearch(String userId, VacancyFilter filter) throws Exception {
        OptionalInt autosearchOptional = getIdByFilter(filter);
        Autosearch autosearch;
        User user;
        if (autosearchOptional.isEmpty()) {
            autosearch = new Autosearch();
            autosearch.setFilter(filter);
        } else {
            autosearch = autosearchRepository.findById(autosearchOptional.getAsInt()).get();
        }
        user = userService.getById(userId)
                .orElseThrow(() -> new Exception(String.format("user with id : %s not found", userId)));
        user.getAutosearches().add(autosearch);
        return autosearchRepository.save(autosearch);
    }

    @Override
    public void updateAutosearchById(int id) {
        Optional<Autosearch> foundAutosearch = autosearchRepository.findById(id);
        if (foundAutosearch.isEmpty()) {
            return;
        }
        Autosearch autosearch = foundAutosearch.get();
        updateAutosearch(autosearch);
    }

    @Override
    public void updateAllAutosearches() {
        for (Autosearch autosearch : autosearchRepository.findAll()) {
            updateAutosearch(autosearch);
        }
    }

    @Override
    public List<Autosearch> getAutosearchesByUserId(String userId) {
        Optional<User> user = userService.getById(userId);
        if (user.isEmpty()) {
            return List.of();
        }
        return user.get().getAutosearches();
    }

    @Override
    public List<String> getNewVacanciesIdListForAutosearchAndForUser(int autosearchId, String userId) {
        Optional<Autosearch> autosearch = getAutosearchById(autosearchId);
        if (autosearch.isEmpty()) {
            return List.of();
        }
        Optional<User> user = userService.getById(userId);
        if (user.isEmpty()) {
            return List.of();
        }
        List<String> vacanciesIdByAutosearch = getVacanciesIdByAutosearch(autosearch.get());
        List<String> vacanciesIdHistory = user.get().getViewedVacanciesIds();
        vacanciesIdByAutosearch.removeAll(vacanciesIdHistory);
        return vacanciesIdByAutosearch;
    }

    private void updateAutosearch(Autosearch autosearch) {
        List<String> curVacanciesIdByThisAutosearch = getVacanciesIdByAutosearch(autosearch);
        autosearch.setLastDaySelectedBySearch(curVacanciesIdByThisAutosearch);
        autosearchRepository.save(autosearch);
    }

    private List<String> getVacanciesIdByAutosearch(Autosearch autosearch) {
        try {
            return vacancyService.getVacanciesByFilters(filterService.toMultiValueMap(autosearch.getFilter())).stream()
                    .map(Vacancy::getId)
                    .collect(Collectors.toList());
        } catch (FilterValidationException e) {
            e.printStackTrace();
            return List.of();
        }
    }



}
