package com.nutrymaco.jobsite.controller;

import com.nutrymaco.jobsite.entity.HREmployee;
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
    TokenService tokenService;

    @Autowired
    HREmployeeService employeeService;

    @GetMapping("/{employeeId}")
    ResponseEntity<?> getEmployee(@PathVariable int companyId,
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
        HREmployee employee = employeeService.getById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("")
    ResponseEntity<?> addEmployee(@PathVariable int companyId, HREmployee employee) {
        HREmployee addedEmployee = employeeService.save(employee);
        return ResponseEntity
                .status(201)
                .body(addedEmployee);
    }

    @PutMapping("/{employeeId}")
    ResponseEntity<?> updateEmployee(@PathVariable int companyId,
                                     @PathVariable String employeeId,
                                     HREmployee employee) {
        /*
            auth
         */

        HREmployee updatedEmployee = employeeService.updateById(employeeId, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/{employeeId}")
    ResponseEntity<?> patchEmployee(@PathVariable int companyId,
                                    @PathVariable String employeeId,
                                    HREmployee employee) throws EmployeeNotFoundException {
        /*
            auth
         */
        HREmployee patchedEmployee = employeeService.patchById(employeeId, employee);
        return ResponseEntity.ok(patchedEmployee);
    }
}
