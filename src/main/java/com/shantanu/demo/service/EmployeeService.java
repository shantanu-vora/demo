package com.shantanu.demo.service;

import com.shantanu.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeById(int employeeId);
    List<Employee> getAllEmployees();
}
