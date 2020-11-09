package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.CompanyDocumentDTO;
import com.nutrymaco.jobsite.exception.found.CompanyDocumentNotFoundException;
import com.nutrymaco.jobsite.service.company.CompanyDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/documents")
public class CompanyDocumentController {

    @Autowired
    private CompanyDocumentService documentService;

    @GetMapping("/{documentId}")
    public ResponseEntity<CompanyDocumentDTO> getDocument(@PathVariable Integer companyId,
                                         @PathVariable Integer documentId) throws CompanyDocumentNotFoundException {
        /*
            auth to company's employee or admin
         */
        CompanyDocumentDTO companyDocument = documentService.getById(documentId);
        return ResponseEntity.ok(companyDocument);
    }

    @PostMapping("/")
    public ResponseEntity<CompanyDocumentDTO> createDocument(@PathVariable Integer companyId,
                                            @RequestBody CompanyDocumentDTO document) {
        /*
            auth to company's employee or admin
         */
        CompanyDocumentDTO savedDocument = documentService.save(document);
        return ResponseEntity
                .status(201)
                .body(savedDocument);
    }

}
