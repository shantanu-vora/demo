package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private EmployeeService employeeService;

//	@MockBean
//	private ProductService productService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@DisplayName("JUnit test getAllEmployees method")
	@Test
	@WithMockUser(roles = {"admin", "user"})
	public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
		Employee employee1 = new Employee("EMP_00001", "Sai", "sai@gmail.com", 10000.00);
		Employee employee2 = new Employee("EMP_00002", "Sarathi", "sarathi@gmail.com", 10000.0);
		List<Employee> employeeList = new ArrayList<>(List.of(employee1, employee2));
		when(employeeService.getAllEmployees()).thenReturn(employeeList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(employeeList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
 	}

	@DisplayName("Junit test for getEmployeeById")
	@Test
	@WithMockUser(roles = {"admin", "user"})
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
		String employeeId = "EMP_00001";
		Employee employee = new Employee("EMP_00001", "Pranaya", "pranaya@gmail.com", 120000.0);

		when(employeeService.getEmployeeById(employee.getId())).thenReturn(employee);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/{id}", employeeId);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(employee);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}



	@DisplayName("Junit test for addEmployee method")
	@Test
	@WithMockUser(roles = "admin")
	void givenEmployeeObject_whenAddEmployee_thenReturnSavedEmployee () throws Exception {
		Employee employee = new Employee("EMP_00001", "Pranaya", "pranaya@gmail.com", 120000.0);
		String inputInJson = this.mapToJson(employee);
		ObjectNode jsonObject = objectMapper.convertValue(employee, ObjectNode.class);
		when(employeeService.saveEmployee(jsonObject)).thenReturn(employee);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees")
			.accept(MediaType.APPLICATION_JSON).content(inputInJson)
			.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@DisplayName("Junit test for updateEmployee")
	@Test
	@WithMockUser(roles = "admin")
	void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception {
		String employeeId = "EMP_00001";
		Employee savedEmployee = new Employee(employeeId, "Sarathi", "sarathi@gmail.com", 10000.0);
		Employee updatedEmployee = new Employee(employeeId, "Sarathi Elangovan", "sarathi@gmail.com", 15000.0);
		String inputInJson = this.mapToJson(updatedEmployee);
		ObjectNode jsonObject = objectMapper.convertValue(updatedEmployee, ObjectNode.class);

		when(employeeService.getEmployeeById(employeeId)).thenReturn(savedEmployee);
		when(employeeService.updateEmployee(employeeId, jsonObject)).thenReturn(updatedEmployee);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/employees/{id}", employeeId)
			.accept(MediaType.APPLICATION_JSON).content(inputInJson)
			.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}


	@DisplayName("JUnit test for deleteProduct method")
	@Test
	@WithMockUser(roles = "admin")
	void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
		String employeeId = "EMP_00001";
		willDoNothing().given(employeeService).deleteEmployee(employeeId);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/employees/{id}", employeeId);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo("Employee deleted successfully!");
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}