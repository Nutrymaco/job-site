package com.nutrymaco.jobsite.mapping;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrymaco.jobsite.dto.CompanyDocumentDTO;
import com.nutrymaco.jobsite.entity.CompanyDocument;
import com.nutrymaco.jobsite.service.company.CompanyDocumentService;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CompanyDocumentMappingTest {

    @Autowired
    CompanyDocumentService documentService;

    ObjectMapper mapper = new ObjectMapper();

    CompanyDocumentDTO dto = new CompanyDocumentDTO();

    Integer id = 1;

    byte[] bytes = "doc data weg1".getBytes();

    String description = "description for doc";

    @Before
    public void initDTO() {
        dto.setId(id);
        dto.setDoc(bytes);
        dto.setDescription(description);
    }

    @Test
    public void test_ToDTO_FromDTO() throws JsonProcessingException {
        CompanyDocument docFromDTO = documentService.fromDto(dto);
        CompanyDocumentDTO dtoFromDoc = documentService.toDTO(docFromDTO);

        String expectedDTO = mapper.writeValueAsString(dto);
        String actualDTO = mapper.writeValueAsString(dtoFromDoc);

        assertEquals(expectedDTO, actualDTO);
    }

}
