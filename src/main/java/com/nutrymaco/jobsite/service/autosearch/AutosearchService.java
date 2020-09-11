package com.nutrymaco.jobsite.service.autosearch;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.entity.Vacancy;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface AutosearchService {
    Optional<Autosearch> getAutosearchById(int id);
    List<Autosearch> getAll();
    boolean exists(Autosearch autosearch);
    Autosearch addAutosearch(String userId, VacancyFilter filter) throws Exception;
    void updateAutosearchById(int id);
    void updateAllAutosearches();
    List<Autosearch> getAutosearchesByUserId(String userId);
    List<Vacancy> getNewVacanciesForAutosearchAndUser(int autosearchId, String userId);
}
