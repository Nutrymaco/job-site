package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.dto.HREmployeeDTO;
import com.nutrymaco.jobsite.entity.HREmployee;
import com.nutrymaco.jobsite.exception.found.CompanyNotFoundException;
import com.nutrymaco.jobsite.exception.found.EmployeeNotFoundException;

public interface HREmployeeService {
    HREmployeeDTO save(HREmployeeDTO employee);
    HREmployeeDTO updateById(String id, HREmployeeDTO update);
    HREmployeeDTO patchById(String id, HREmployeeDTO patch) throws EmployeeNotFoundException, CompanyNotFoundException;
    HREmployeeDTO getById(String employeeId) throws EmployeeNotFoundException;
    HREmployeeDTO getByEmail(String email) throws EmployeeNotFoundException;
    HREmployee fromDTO(HREmployeeDTO dto);
    HREmployeeDTO toDTO(HREmployee employee);
}
