package com.nutrymaco.jobsite.dto;

import com.nutrymaco.jobsite.entity.CompanyDocument;
import com.nutrymaco.jobsite.entity.HREmployee;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CompanyDTO {

    private Integer id;

    private String name;

    private Boolean approved;

}
