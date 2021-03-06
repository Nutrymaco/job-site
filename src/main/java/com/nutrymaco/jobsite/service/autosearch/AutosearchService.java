package com.nutrymaco.jobsite.service.autosearch;

import com.nutrymaco.jobsite.dto.VacancyDTO;
import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.exception.found.AutosearchNotFoundException;
import com.nutrymaco.jobsite.exception.found.UserNotFoundException;

import java.util.List;

public interface AutosearchService {
    Autosearch getAutosearchById(int id) throws AutosearchNotFoundException;
    List<Autosearch> getAll();
    boolean exists(Autosearch autosearch);
    Autosearch addAutosearch(String userId, VacancyFilter filter) throws UserNotFoundException;
    void updateAutosearchById(int id);
    void updateAllAutosearches();
    List<VacancyDTO> getNewVacanciesForAutosearchAndUser(int autosearchId, String userId) throws AutosearchNotFoundException, UserNotFoundException;
}
