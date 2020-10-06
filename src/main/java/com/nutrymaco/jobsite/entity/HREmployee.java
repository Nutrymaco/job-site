package com.nutrymaco.jobsite.entity;

import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;


@Data
@Entity
public class HREmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

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

