package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.entity.HREmployee;
import com.nutrymaco.jobsite.exception.found.EmployeeNotFoundException;
import com.nutrymaco.jobsite.repository.HREmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HREmployeeServiceImpl implements HREmployeeService {

    @Autowired
    HREmployeeRepository employeeRepository;

    @Override
    public HREmployee save(HREmployee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public HREmployee updateById(String id, HREmployee update) {
        update.setId(id);
        return employeeRepository.save(update);
    }

    @Override
    public HREmployee patchById(String id, HREmployee patch) throws EmployeeNotFoundException {
        HREmployee employee = getById(id);
        if (patch.getFirstName() != null) {
            employee.setFirstName(patch.getFirstName());
        }
        if (patch.getLastName() != null) {
            employee.setLastName(patch.getLastName());
        }
        if (patch.getCompany() != null) {
            employee.setCompany(patch.getCompany());
        }
        if (patch.getEmail() != null) {
            employee.setEmail(patch.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public HREmployee getById(String employeeId) throws EmployeeNotFoundException {
        Optional<HREmployee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        return employee.get();
    }

    @Override
    public HREmployee getByEmail(String email) throws EmployeeNotFoundException {
        Optional<HREmployee> employee = employeeRepository.findByEmail(email);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        return employee.get();
    }
}
