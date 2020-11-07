package com.nutrymaco.jobsite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class CompanyDocumentDTO {

    private Integer id;

    private byte[] doc;

    private String description;

}
