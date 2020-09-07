package com.nutrymaco.jobsite.dto.response;


import com.nutrymaco.jobsite.entity.Autosearch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

        private List<String> newVacanciesIdList;

        private int count;
    }

    public class AutosearchByUserAdder {
        private AutosearchByUser autosearchByUser = new AutosearchByUser();

        public AutosearchByUserAdder setAutosearch(Autosearch autosearch) {
            autosearchByUser.setAutosearch(autosearch);
            return this;
        }

        public AutosearchByUserAdder setNewVacanciesIdList(List<String> newVacanciesIdList) {
            autosearchByUser.setNewVacanciesIdList(newVacanciesIdList);
            return this;
        }

        public AutosearchByUserAdder addNewVacancyId(String vacancyId) {
            autosearchByUser.getNewVacanciesIdList().add(vacancyId);
            return this;
        }

        public void add() {
            if (autosearchByUser.getAutosearch() == null || autosearchByUser.getNewVacanciesIdList() == null){
                throw new IllegalStateException();
            }
            autosearchByUser.setCount(autosearchByUser.getNewVacanciesIdList().size());
            autosearchesByUser.add(autosearchByUser);
        }
    }
}
