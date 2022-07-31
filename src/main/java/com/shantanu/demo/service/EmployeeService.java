package com.shantanu.demo.service;

import com.shantanu.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeById(String employeeId);
    List<Employee> getAllEmployees();

//    Employee saveEmployee(ObjectNode jsonObject);
}
