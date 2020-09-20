package com.nutrymaco.jobsite.dto.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Option {
    int value;
    String name;

    public Option(int value, String name) {
        this.value = value;
        this.name = name;
    }
}