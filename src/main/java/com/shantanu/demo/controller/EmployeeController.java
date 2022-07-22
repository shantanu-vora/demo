package com.shantanu.demo.controller;

import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl service;

    @GetMapping("/{employeeId}")
    @RolesAllowed("user")
    public ResponseEntity<Employee> getEmployee(@PathVariable int employeeId) {
        return ResponseEntity.ok(service.getEmployee(employeeId));
    }

    @GetMapping
    @RolesAllowed("admin")
    public ResponseEntity<List<Employee>> findAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }
}