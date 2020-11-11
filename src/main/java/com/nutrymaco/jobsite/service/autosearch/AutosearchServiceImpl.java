package com.nutrymaco.jobsite.service.autosearch;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.exception.found.AutosearchNotFoundException;
import com.nutrymaco.jobsite.exception.found.UserNotFoundException;
import com.nutrymaco.jobsite.exception.validation.FilterValidationException;
import com.nutrymaco.jobsite.repository.AutosearchRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import com.nutrymaco.jobsite.service.city.CityService;
import com.nutrymaco.jobsite.service.user.UserService;
import com.nutrymaco.jobsite.service.vacancy.VacancyFilterService;
import com.nutrymaco.jobsite.service.vacancy.VacancyService;
import com.nutrymaco.jobsite.service.workschedule.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @Autowired
    CityService cityService;

    @Autowired
    WorkScheduleService scheduleService;


    @Override
    public Autosearch getAutosearchById(int id) throws AutosearchNotFoundException {
        return autosearchRepository
                .findById(id)
                .orElseThrow(AutosearchNotFoundException::new);
    }

    @Override
    public List<Autosearch> getAll() {
        return (List<Autosearch>) autosearchRepository.findAll();
    }

    @Override
    public boolean exists(Autosearch autosearch) {
        return exists(autosearch.extractFilter());
    }

    public boolean exists(VacancyFilter filter) {
        return !getAllByFilter(filter).isEmpty();
    }

//    @Override
//    public void addAutosearch(Autosearch autosearch) {
//        if (!exists(autosearch)) {
//            autosearchRepository.save(autosearch);
//        }
//    }

    //todo mb need to opt equals city and ws
    public List<Autosearch> getAllByFilter(VacancyFilter filter) {
        return autosearchRepository.findByTextAndExperienceAndSalary(
                filter.getText(),
                filter.getExperience(),
                filter.getSalary()).stream()
                    .filter(a -> a.getCities().equals(cityService.getAllById(filter.getCityIdList())))
                    .filter(a -> a.getWorkSchedules().equals(scheduleService.getAllById(filter.getWorkScheduleIdList())))
                    .collect(Collectors.toList());
    }

    public Optional<Autosearch> getFirstByFilter(VacancyFilter filter) {
        List<Autosearch> autosearches = getAllByFilter(filter);
        return autosearches.size() > 0 ?
                Optional.of(autosearches.get(0)) : Optional.empty();
    }

    @Override
    public Autosearch addAutosearch(String userId, VacancyFilter filter) throws UserNotFoundException {
        Optional<Autosearch> autosearchOptional = getFirstByFilter(filter);
        Autosearch autosearch;
        if (autosearchOptional.isEmpty()) {
            autosearch = new Autosearch();
            autosearch.setText(filter.getText());
            autosearch.setExperience(filter.getExperience());
            autosearch.setSalary(filter.getSalary());
            autosearch.setCities(cityService.getAllById(filter.getCityIdList()));
            autosearch.setWorkSchedules(scheduleService.getAllById(filter.getWorkScheduleIdList()));
            autosearch = autosearchRepository.save(autosearch);
            updateAutosearch(autosearch);
        } else {
            autosearch = autosearchOptional.get();
        }
        userService.addAutosearch(userId, autosearch);
        return autosearch;
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
    public List<VacancyDTO> getNewVacanciesForAutosearchAndUser(int autosearchId, String userId) throws AutosearchNotFoundException, UserNotFoundException {
        Autosearch autosearch = getAutosearchById(autosearchId);
        List<VacancyDTO> vacanciesByAutosearch = getVacanciesByAutosearch(autosearch);
        List<String> vacanciesIdHistory = userService.getViewedVacanciesIds(userId);
        vacanciesByAutosearch.removeIf(vacancy -> vacanciesIdHistory.contains(vacancy.getId()));
        return vacanciesByAutosearch;
    }

    private void updateAutosearch(Autosearch autosearch) {
        List<String> curVacanciesIdByThisAutosearch = getVacanciesIdByAutosearch(autosearch);
        autosearch.setLastDaySelectedBySearch(curVacanciesIdByThisAutosearch);
        autosearchRepository.save(autosearch);
    }

    private List<VacancyDTO> getVacanciesByAutosearch(Autosearch autosearch) {
        try {
            return vacancyService.getVacanciesByFilters(autosearch.extractFilter());
        } catch (FilterValidationException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private List<String> getVacanciesIdByAutosearch(Autosearch autosearch) {
        try {
            return vacancyService.getVacanciesByFilters(autosearch.extractFilter()).stream()
                    .map(VacancyDTO::getId)
                    .collect(Collectors.toList());
        } catch (FilterValidationException e) {
            e.printStackTrace();
            return List.of();
        }
    }



}
