package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.service.EmployeeService;
import com.shantanu.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private EmployeeService employeeService;

	@MockBean
	private ProductService productService;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@WithMockUser(roles = "admin")
	public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
		Employee employee1 = new Employee("sai", 10000);
		Employee employee2 = new Employee("sarathi", 10000);
		Employee employee3 = new Employee("pranaya", 10000);
		List<Employee> employeeList = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
		when(employeeService.getAllEmployees()).thenReturn(employeeList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expectedJson = this.mapToJson(employeeList);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
 	}

	@Test
	@WithMockUser(roles = "user")
	public void givenEmployeeId_whenGetEmployeeId_thenReturnEmployeeObject() throws Exception {
		int employeeId = 1;
		Employee employee = Employee.builder()
			.id(1)
			.name("pranaya")
			.salary(12000).build();

		when(employeeService.getEmployeeById(employee.getId())).thenReturn(employee);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees/{id}", employeeId).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(employee);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}