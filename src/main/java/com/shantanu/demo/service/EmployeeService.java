package com.shantanu.demo.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeById(String employeeId);
    List<Employee> getAllEmployees();
    Employee saveEmployee(ObjectNode jsonObject);
    //    Product updateProduct(String id, Product product);
    Employee updateEmployee(String id, ObjectNode jsonObject);
    void deleteEmployee(String id);
}
