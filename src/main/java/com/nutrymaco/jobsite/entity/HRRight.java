package com.nutrymaco.jobsite.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class HRRight {
    @Id
    Integer id;

    @Enumerated(EnumType.ORDINAL)
    HRRightType rightType;
}
