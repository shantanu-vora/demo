package com.shantanu.demo.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin","user"})
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") @Pattern(regexp = "^EMP_\\d{5}$") String employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    @RolesAllowed("admin")
    public ResponseEntity<Employee> addEmployee(@RequestBody ObjectNode jsonObject) {
        System.out.println(jsonObject);
        Employee employee = employeeService.saveEmployee(jsonObject);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") @Pattern(regexp = "^EMP_\\d{5}$") String id,
                                                 @RequestBody ObjectNode jsonObject) {
        Employee employee = employeeService.updateEmployee(id, jsonObject);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") @Pattern(regexp = "^EMP_\\d{5}$") String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).body("Employee deleted successfully!");
    }
}