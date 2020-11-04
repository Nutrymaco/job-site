package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.CompanyDocument;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<CompanyDocument, Integer> {

}
