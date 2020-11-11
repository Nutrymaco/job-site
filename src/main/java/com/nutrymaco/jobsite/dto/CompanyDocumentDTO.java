package com.nutrymaco.jobsite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDocumentDTO {

    private Integer id;

    private byte[] doc;

    private String description;

}
