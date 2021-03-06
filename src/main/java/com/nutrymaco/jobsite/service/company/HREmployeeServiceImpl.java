package com.nutrymaco.jobsite.service.company;

import com.nutrymaco.jobsite.dto.HREmployeeDTO;
import com.nutrymaco.jobsite.entity.HREmployee;
import com.nutrymaco.jobsite.exception.found.CompanyNotFoundException;
import com.nutrymaco.jobsite.exception.found.EmployeeNotFoundException;
import com.nutrymaco.jobsite.repository.HREmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HREmployeeServiceImpl implements HREmployeeService {

    @Autowired
    HREmployeeRepository employeeRepository;

    @Autowired
    CompanyService companyService;

    @Override
    public HREmployeeDTO save(HREmployeeDTO employee) {
        return toDTO(employeeRepository.save(fromDTO(employee)));
    }

    @Override
    public HREmployeeDTO updateById(String id, HREmployeeDTO update) {
        update.setId(id);
        return toDTO(employeeRepository.save(fromDTO(update)));
    }

    @Override
    public HREmployeeDTO patchById(String id, HREmployeeDTO patch) throws EmployeeNotFoundException, CompanyNotFoundException {
        HREmployee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        if (patch.getFirstName() != null) {
            employee.setFirstName(patch.getFirstName());
        }
        if (patch.getLastName() != null) {
            employee.setLastName(patch.getLastName());
        }
        if (patch.getEmail() != null) {
            employee.setEmail(patch.getEmail());
        }
        return toDTO(employeeRepository.save(employee));
    }

    @Override
    public HREmployeeDTO getById(String employeeId) throws EmployeeNotFoundException {
        return employeeRepository.findById(employeeId)
                .map(this::toDTO)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public HREmployeeDTO getByEmail(String email) throws EmployeeNotFoundException {
        return employeeRepository.findByEmail(email)
                .map(this::toDTO)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public HREmployee fromDTO(HREmployeeDTO dto) {
        return employeeRepository.findById(dto.getId())
                .map(e -> {
                    if (dto.getFirstName() != null)
                        e.setFirstName(dto.getFirstName());
                    if (dto.getLastName() != null)
                        e.setLastName(dto.getLastName());
                    if (dto.getEmail() != null)
                        e.setLastName(dto.getEmail());
                    return e;
                })
                .orElseGet(() -> {
                    HREmployee employee = new HREmployee();
                    employee.setId(dto.getId());
                    employee.setFirstName(dto.getFirstName());
                    employee.setLastName(dto.getLastName());
                    employee.setEmail(dto.getEmail());
                    employee.setRights(new ArrayList<>());
                    return employee;
                });
    }

    @Override
    public HREmployeeDTO toDTO(HREmployee employee) {
        HREmployeeDTO dto = new HREmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        return dto;
    }
}
