package com.nutrymaco.jobsite.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;


@Entity
public class HREmployee extends SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    private Company company;

    @ElementCollection(targetClass = HRRight.class)
    @CollectionTable(
            name = "HRRight",
            joinColumns = @JoinColumn(name = "id")
    )
    @Column(name = "hrright_id")
    private List<HRRight> rights;
}

