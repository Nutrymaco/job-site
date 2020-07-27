package com.nutrymaco.jobsite.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonTypeName("autocomplete")
public class Autocomplete {
    List<String> options = new ArrayList<>();
    int count;

    public void addOption(String option) {
        options.add(option);
    }
}
