package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.dto.HREmployeeDTO;
import com.nutrymaco.jobsite.entity.HREmployee;
import com.nutrymaco.jobsite.exception.found.CompanyNotFoundException;
import com.nutrymaco.jobsite.exception.found.EmployeeNotFoundException;
import com.nutrymaco.jobsite.service.auth.TokenService;
import com.nutrymaco.jobsite.service.company.HREmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/companies/{companyId}/employees")
public class HREmployeeController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HREmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<HREmployeeDTO> getEmployee(@PathVariable int companyId,
                                  @PathVariable String employeeId,
                                  HttpServletRequest request) throws EmployeeNotFoundException {
        /*
            authentication

        String employeeIdByToken = tokenService.getEmployeeIdByToken(token);
        HREmployee employee = employeeService.getById(employeeIdByToken);
        if (!employeeService.employeeHasRightsOn(employeeId)) {
            throw error
         */

        /*
         */
        HREmployeeDTO employee = employeeService.getById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("")
    public ResponseEntity<HREmployeeDTO> addEmployee(@PathVariable int companyId, HREmployeeDTO employee) {
        HREmployeeDTO addedEmployee = employeeService.save(employee);
        return ResponseEntity
                .status(201)
                .body(addedEmployee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<HREmployeeDTO> updateEmployee(@PathVariable int companyId,
                                     @PathVariable String employeeId,
                                                     HREmployeeDTO employee) {
        /*
            auth
         */

        HREmployeeDTO updatedEmployee = employeeService.updateById(employeeId, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<HREmployeeDTO> patchEmployee(@PathVariable int companyId,
                                    @PathVariable String employeeId,
                                                    HREmployeeDTO employee) throws EmployeeNotFoundException, CompanyNotFoundException {
        /*
            auth
         */
        HREmployeeDTO patchedEmployee = employeeService.patchById(employeeId, employee);
        return ResponseEntity.ok(patchedEmployee);
    }
}
