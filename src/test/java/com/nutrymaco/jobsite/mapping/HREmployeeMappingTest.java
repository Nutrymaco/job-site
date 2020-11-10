package com.nutrymaco.jobsite.mapping;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrymaco.jobsite.dto.CompanyDTO;
import com.nutrymaco.jobsite.dto.HREmployeeDTO;
import com.nutrymaco.jobsite.entity.HREmployee;
import com.nutrymaco.jobsite.service.company.HREmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
public class HREmployeeMappingTest {

    @Autowired
    HREmployeeService employeeService;

    ObjectMapper mapper = new ObjectMapper();

    HREmployeeDTO dto = new HREmployeeDTO();

    private String id = "rgqer";

    private String firstName = "Efim";

    private String lastName = "Smykov";

    private String email = "efim@email.com";


    @Before
    public void initDTO() {
        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
    }


    @Test
    public void testFromDTOToDTO() throws JsonProcessingException {
        HREmployee employee = employeeService.fromDTO(dto);
        HREmployeeDTO dtoFromEmployee = employeeService.toDTO(employee);

        String expectedDTO = mapper.writeValueAsString(dto);
        String actualDTO = mapper.writeValueAsString(dtoFromEmployee);

        assertEquals(expectedDTO, actualDTO);
    }

}
