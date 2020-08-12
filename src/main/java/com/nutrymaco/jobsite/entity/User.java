package com.nutrymaco.jobsite.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "site_user")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class User {
    @Id
    long id;

    String username;

    @Type(type = "jsonb")
    List<String> viewedVacanciesIds;
}
