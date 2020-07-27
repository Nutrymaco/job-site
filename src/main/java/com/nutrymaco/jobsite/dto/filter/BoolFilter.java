package com.nutrymaco.jobsite.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoolFilter extends Filter {
    final FilterType type = FilterType.BOOL;
    private String name;
    private String option;
}
