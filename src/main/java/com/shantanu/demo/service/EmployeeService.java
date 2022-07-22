package com.shantanu.demo.service;

import com.shantanu.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getEmployee(int employeeId);
    List<Employee> getAllEmployees();
}
