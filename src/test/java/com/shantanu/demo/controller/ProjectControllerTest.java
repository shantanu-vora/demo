package com.shantanu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanu.demo.entity.Project;
import com.shantanu.demo.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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
import static org.mockito.Mockito.when;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

	private MockMvc mockMvc;

	@MockBean
	private ProjectService projectService;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@DisplayName("Junit test for getAllProjects method")
	@Test
	@WithMockUser(roles = {"user","admin"})
	void givenListOfProjects_whenGetAllProjects_thenReturnProjectList() throws Exception {
		Project project1 = new Project("PROJ_00001", "Project1");
		Project project2 = new Project("PROJ_00002", "Project2");
		List<Project> projectList = new ArrayList<>(List.of(project1, project2));
		when(projectService.getAllProjects()).thenReturn(projectList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/projects");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expectedJson = this.mapToJson(projectList);
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@DisplayName("Junit test for getProjectById method")
	@Test
	@WithMockUser(roles = {"user", "admin"})
	void givenProjectId_whenGetProjectById_thenReturnProjectObject() throws Exception {
		String projectId = "PROJ_00001";
		Project project = new Project("PROJ_00001", "Project1");

		when(projectService.getProjectById(project.getId())).thenReturn(project);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/projects/{id}", projectId);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String expectedJson = this.mapToJson(project);
		String outputInJson = response.getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

}