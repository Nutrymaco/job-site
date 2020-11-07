package com.nutrymaco.jobsite.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "site_user")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class User extends BaseUser {

    Issuer issuer;

    String name;

    String surname;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    List<String> viewedVacanciesIds;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Autosearch> autosearches;
}
