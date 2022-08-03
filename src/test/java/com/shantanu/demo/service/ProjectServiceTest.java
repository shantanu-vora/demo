package com.shantanu.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanu.demo.entity.Project;
import com.shantanu.demo.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProjectServiceTest {

	@MockBean
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ObjectMapper objectMapper;

	private Project project1;

	@BeforeEach
	public void setup() {
		project1 = new Project("PROJ_00001", "Project1");
	}

	@DisplayName("JUnit test for getProjectById method")
	@Test
	void givenProjectId_whenGetProjectById_thenReturnProjectObject() {
		Project project2 = new Project("PROJ_00002", "Project2");
		when(projectRepository.findById("PROJ_00002")).thenReturn(Optional.of(project2));
		Project savedProject = projectService.getProjectById("PROJ_00002");
		assertThat(savedProject).isNotNull();
		assertEquals(project2, savedProject);
	}

	@DisplayName("JUnit test for getAllProjects method")
	@Test
	void givenProjectsList_whenGetAllProjects_thenReturnProjectList() {
		Project project2 = new Project("PROJ_00002", "Project2");
		when(projectRepository.findAll()).thenReturn(List.of(this.project1, project2));
		List<Project> projectList = projectService.getAllProjects();
		assertThat(projectList).isNotNull();
		assertThat(projectList.size()).isEqualTo(2);
	}

	@DisplayName("JUnit test for getProjectsWithCostLessThan method")
	@Test
	void givenProjectsList_whenGetProjectsWithCostLessThan_thenReturnProjectsList() {
		Project project2 = new Project("PROJ_00002", "Project2");
		Double cost = 100000.0;

		when(projectRepository.findProjectsWithCostLessThan(cost)).thenReturn(List.of(this.project1, project2));
		List<Project> projectList = projectService.getProjectsWithCostLessThan(cost);
		assertThat(projectList).isNotNull();
		assertThat(projectList.size()).isEqualTo(2);
	}
}