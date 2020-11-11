package com.nutrymaco.jobsite.dto.response;


import com.nutrymaco.jobsite.entity.Autosearch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class AutosearchResponse {

    private final List<AutosearchByUser> autosearchesByUser = new ArrayList<>();

    public AutosearchByUserAdder autosearchByUserAdder() {
        return new AutosearchByUserAdder();
    }

    @Setter
    @Getter
    public class AutosearchByUser {
        private Autosearch autosearch;

        private int countOfNewVacancies;
    }

    public class AutosearchByUserAdder {
        private final AutosearchByUser autosearchByUser = new AutosearchByUser();

        public AutosearchByUserAdder setAutosearch(Autosearch autosearch) {
            autosearchByUser.setAutosearch(autosearch);
            return this;
        }

        public AutosearchByUserAdder setcountOfNewVacancies(int countOfNewVacancies) {
            autosearchByUser.setCountOfNewVacancies(countOfNewVacancies);
            return this;
        }

        public void add() {
            if (autosearchByUser.getAutosearch() == null){
                throw new IllegalStateException();
            }
            autosearchesByUser.add(autosearchByUser);
        }
    }
}
