package com.nutrymaco.jobsite.service.autosearch;

import com.nutrymaco.jobsite.entity.Autosearch;

import java.util.List;
import java.util.Optional;

public interface AutosearchService {
    Optional<Autosearch> findById(int id);
    List<Autosearch> getAll();
    boolean exists(Autosearch autosearch);
    void addAutosearch(Autosearch autosearch);
    void updateAutosearchById(int id);
}
