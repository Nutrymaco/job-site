package com.nutrymaco.jobsite.dto;

import com.nutrymaco.jobsite.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HREmployeeDTO extends BaseUserDTO {
    private CompanyDTO company;
}
