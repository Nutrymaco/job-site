package com.nutrymaco.jobsite.service.autosearch;

import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.entity.Vacancy;
import com.nutrymaco.jobsite.exception.validation.FilterValidationException;
import com.nutrymaco.jobsite.repository.AutosearchRepository;
import com.nutrymaco.jobsite.service.vacancy.VacancyService;
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
    AutosearchRepository autosearchRepository;

    @Override
    public Optional<Autosearch> findById(int id) {
        return autosearchRepository.findById(id);
    }

    @Override
    public List<Autosearch> getAll() {
        return (List<Autosearch>) autosearchRepository.findAll();
    }

    @Override
    public boolean exists(Autosearch autosearch) {
        return autosearchRepository.findByFilters(autosearch.getFilters()).
                isPresent();
    }

    @Override
    public void addAutosearch(Autosearch autosearch) {
        if (!exists(autosearch)) {
            autosearchRepository.save(autosearch);
        }
    }

    @Override
    public void updateAutosearchById(int id) {
        Optional<Autosearch> findedAutosearch = autosearchRepository.findById(id);
        if (findedAutosearch.isEmpty()) {
            return;
        }
        Autosearch autosearch = findedAutosearch.get();
        List<String> curVacanciesIdByThisAutosearch = getVacanciesIdByAutosearch(autosearch);
        autosearch.setLastDaySelectedBySearch(curVacanciesIdByThisAutosearch);
        autosearchRepository.save(autosearch);
    }


    private List<String> getVacanciesIdByAutosearch(Autosearch autosearch) {
        try {
            return vacancyService.getVacanciesByFilters(autosearch.getFilters()).stream()
                    .map(Vacancy::getId)
                    .collect(Collectors.toList());
        } catch (FilterValidationException e) {
            e.printStackTrace();
            return List.of();
        }
    }

}
