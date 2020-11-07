package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.CompanyDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyDocumentRepository extends CrudRepository<CompanyDocument, Integer> {

    Optional<CompanyDocument> findById(int id);

}
