package com.shantanu.demo.service;

import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.exception.CustomException;
import com.shantanu.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
public class EmployeeServiceTest {

	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	private Employee employee1;

	@BeforeEach
	public void setup() {
		employee1 = Employee.builder()
			.id(1)
			.name("puneet")
			.salary(10000)
			.build();
	}

	@DisplayName("JUnit test for getAllEmployees method")
	@Test
	public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
		Employee employee2 = Employee.builder()
			.id(2)
			.name("pranaya")
			.salary(12000)
			.build();

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
		Employee employee2 = Employee.builder()
			.id(2)
			.name("pranaya")
			.salary(12000).build();
		when(employeeRepository.findById(2)).thenReturn(Optional.ofNullable(employee2));
		Employee savedEmployee = employeeService.getEmployeeById(2);
		assertThat(savedEmployee).isNotNull();
		assertEquals(employee2, savedEmployee);
	}

	@DisplayName("JUnit test for getEmployeeById method (negative scenario)")
	@Test
	public void givenEmployeeId_whenEmployeeIdDoesNotExist_thenThrowRuntimeException() {
		Employee employee2 = Employee.builder()
			.id(2)
			.name("pranaya")
			.salary(12000).build();
		when(employeeRepository.findById(2)).thenReturn(Optional.of(employee2));
		assertThrows(CustomException.class,()-> employeeService.getEmployeeById(9));
	}
}