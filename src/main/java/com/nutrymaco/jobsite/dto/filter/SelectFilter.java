package com.nutrymaco.jobsite.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SelectFilter extends Filter {
    final FilterType type = FilterType.SELECT;
    String name;
    List<Option> options;
    boolean multiple;
}
