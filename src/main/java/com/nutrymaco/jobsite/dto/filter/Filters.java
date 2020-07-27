package com.nutrymaco.jobsite.dto.filter;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("filters")
@Getter
public class Filters {
    List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter f) {
        filters.add(f);
    }
}
