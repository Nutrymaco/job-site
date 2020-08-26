package com.nutrymaco.jobsite.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "site_user")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class User {
    @Id
    String id;

    String name;

    String surname;

    Issuer issuer;

    @Type(type = "jsonb")
    List<String> viewedVacanciesIds;

    @ManyToMany
    List<Autosearch> autosearches;
}
