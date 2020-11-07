package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.dto.CompanyDocumentDTO;
import com.nutrymaco.jobsite.entity.CompanyDocument;
import com.nutrymaco.jobsite.exception.found.CompanyDocumentNotFoundException;

public interface CompanyDocumentService  {

    CompanyDocumentDTO getById(int id) throws CompanyDocumentNotFoundException;

    CompanyDocument fromDto(CompanyDocumentDTO dto);

    CompanyDocumentDTO toDTO(CompanyDocument document);

    CompanyDocumentDTO save(CompanyDocumentDTO document);

}
