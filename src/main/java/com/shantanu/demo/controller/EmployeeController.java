package com.shantanu.demo.controller;

import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    @RolesAllowed({"admin","user"})
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") @Pattern(regexp = "^EMP_\\d{5}$") String employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    @RolesAllowed("admin")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}