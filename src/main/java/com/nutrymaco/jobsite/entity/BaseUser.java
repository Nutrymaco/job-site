package com.nutrymaco.jobsite.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;


@Getter
@Setter
@MappedSuperclass
public class BaseUser {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String email;
}
