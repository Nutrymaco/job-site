package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.entity.HREmployee;
import com.nutrymaco.jobsite.exception.found.EmployeeNotFoundException;

public interface HREmployeeService {
    HREmployee save(HREmployee employee);
    HREmployee updateById(String id, HREmployee update);
    HREmployee patchById(String id, HREmployee patch) throws EmployeeNotFoundException;
    HREmployee getById(String employeeId) throws EmployeeNotFoundException;
    HREmployee getByEmail(String email) throws EmployeeNotFoundException;
}
