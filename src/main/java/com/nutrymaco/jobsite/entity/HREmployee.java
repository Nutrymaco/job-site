package com.nutrymaco.jobsite.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class HREmployee extends BaseUser {

    @ManyToOne
    private Company company;

    @OneToMany
    private List<HRRight> rights;
}

