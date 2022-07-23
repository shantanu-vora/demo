package com.shantanu.demo.service;

import com.shantanu.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee findById(int employeeId);
    List<Employee> findAll();
}
