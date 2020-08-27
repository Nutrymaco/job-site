package com.nutrymaco.jobsite.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.util.MultiValueMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "autosearch")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Autosearch {
    @Id
    @GeneratedValue
    int id;

    @Type(type = "jsonb")
    MultiValueMap<String, String> filters;

    @Type(type = "jsonb")
    List<String> lastDaySelectedBySearch;

    @ManyToMany
    List<User> users;
}
