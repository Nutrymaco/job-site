package com.nutrymaco.jobsite.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RangeFilter extends Filter {
    final FilterType type = FilterType.RANGE;
    private String name;
    private RangeBoundType rangeBoundType;
    private double from;
    private double to;
}
