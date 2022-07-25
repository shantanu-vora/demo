package com.shantanu.demo.controller;

import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.service.EmployeeService;
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
    private EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    @RolesAllowed("user")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int employeeId) {
//        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(employeeId);
//        if(optionalEmployee.isPresent()) {
//            return ResponseEntity.ok(optionalEmployee.get());
//        } else {
//            throw new CustomException("Employee with employee id " + employeeId + " does not exist");
//        }
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @GetMapping
    @RolesAllowed("admin")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}