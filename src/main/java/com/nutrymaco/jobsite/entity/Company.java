package com.nutrymaco.jobsite.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;


@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    private Boolean approved;

    @OneToMany
    private List<HREmployee> employees;

    @OneToMany
    private List<CompanyDocument> documents;
}
