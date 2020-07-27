package com.nutrymaco.jobsite.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SelectFilter extends Filter {
    final FilterType type = FilterType.SELECT;
    String name;
    List<String> options_name;
    boolean multiple;
}
