package com.shantanu.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@SpringBootTest
public class EmployeeServiceTest {

	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	private Employee employee1;

	@BeforeEach
	public void setup() {
		employee1 = new Employee("EMP_00001", "Sarathi", "sarathi@gmail.com", 10000.0);
	}

	@DisplayName("JUnit test for getAllEmployees method")
	@Test
	public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
		Employee employee2 = new Employee("EMP_00002", "Pranaya", "pranaya@gmail.com", 12000.0);

		when(employeeRepository.findAll()).thenReturn(List.of(this.employee1, employee2));
		List<Employee> employeeList = employeeService.getAllEmployees();
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(2);
	}

	@DisplayName("JUnit test for getAllEmployees method (negative scenario)")
	@Test
	public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
		when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
		List<Employee> employeeList = employeeService.getAllEmployees();
		assertThat(employeeList.isEmpty()).isTrue();
	}

	@DisplayName("JUnit test for getEmployeeById method")
	@Test
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {

		Employee employee2 = new Employee("EMP_00002", "Pranaya", "pranaya@gmail.com", 12000.0);
		when(employeeRepository.findById(employee2.getId())).thenReturn(Optional.of(employee2));
		Employee savedEmployee = employeeService.getEmployeeById("EMP_00002");
		assertThat(savedEmployee).isNotNull();
		assertEquals(employee2, savedEmployee);
	}

	@DisplayName("JUnit test for getEmployeeById method (negative scenario)")
	@Test
	public void givenEmployeeId_whenEmployeeIdDoesNotExist_thenThrowRuntimeException() {
		Employee employee2 = new Employee("EMP_00002", "Pranaya", "pranaya@gmail.com", 12000.0);
		when(employeeRepository.findById(employee2.getId())).thenReturn(Optional.of(employee2));
		assertThrows(HttpClientErrorException.class,()-> employeeService.getEmployeeById("EMP_00012"));
	}

//	@DisplayName("JUnit test for saveEmployee method")
//	@Test
//	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
//		ObjectNode jsonObject = objectMapper.convertValue(employee1, ObjectNode.class);
//		when(employeeRepository.save(employee1)).thenReturn(employee1);
//		when(employeeRepository.findEmployeeByEmail(employee1.getEmail())).thenReturn(null);
//		Employee savedEmployee = employeeService.saveEmployee(jsonObject);
//		System.out.println(savedEmployee);
//		assertThat(savedEmployee).isNotNull();
//		assertEquals(employee1, savedEmployee);
//	}

//	@DisplayName("JUnit test for updateEmployee method")
//	@Test
//	public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
//		ObjectNode jsonObject = objectMapper.convertValue(employee1, ObjectNode.class);
//		when(employeeRepository.save(employee1)).thenReturn(employee1);
//		when(employeeRepository.findById(employee1.getId())).thenReturn(Optional.of(employee1));
//		employee1.setName("Pranaya");
//		employee1.setEmail("pranaya@gmail.com");
//		employee1.setSalary(90000.0);
//		Employee updatedEmployee = employeeService.updateEmployee(employee1.getId(), jsonObject);
//		assertThat(updatedEmployee.getName()).isEqualTo("Pranaya");
//		assertThat(updatedEmployee.getEmail()).isEqualTo("pranaya@gmail.com");
//		assertThat(updatedEmployee.getSalary()).isEqualTo(90000.0);
//		assertEquals(employee1, updatedEmployee);
//	}

	@DisplayName("JUnit test for deleteEmployee method")
	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
		String employeeId = "EMP_00001";
		willDoNothing().given(employeeRepository).delete(employee1);
		when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee1));
		employeeService.deleteEmployee(employeeId);
		verify(employeeRepository, times(1)).delete(employee1);
	}
}