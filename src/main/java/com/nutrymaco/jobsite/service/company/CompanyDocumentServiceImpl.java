package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.dto.CompanyDocumentDTO;
import com.nutrymaco.jobsite.entity.CompanyDocument;
import com.nutrymaco.jobsite.exception.found.CompanyDocumentNotFoundException;
import com.nutrymaco.jobsite.repository.CompanyDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CompanyDocumentServiceImpl implements CompanyDocumentService {

    @Autowired
    CompanyDocumentRepository documentRepository;


    @Override
    public CompanyDocumentDTO getById(int id) throws CompanyDocumentNotFoundException {
        return documentRepository
                .findById(id)
                .map(this::toDTO)
                .orElseThrow(CompanyDocumentNotFoundException::new);
    }

    @Override
    public CompanyDocument fromDto(CompanyDocumentDTO dto) {
        return CompanyDocument.builder()
                .id(dto.getId())
                .doc(dto.getDoc())
                .description(dto.getDescription())
                .build();

    }

    @Override
    public CompanyDocumentDTO toDTO(CompanyDocument entity) {
        return CompanyDocumentDTO.builder()
                .id(entity.getId())
                .doc(entity.getDoc())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public CompanyDocumentDTO save(CompanyDocumentDTO document) {
        return toDTO(documentRepository.save(fromDto(document)));
    }
}
