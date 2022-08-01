package com.shantanu.demo.controller;

import com.shantanu.demo.entity.Project;
import com.shantanu.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Validated
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping
	@RolesAllowed({"admin", "user"})
	public ResponseEntity<List<Project>> getAllProjects() {
		List<Project> projects = projectService.getAllProjects();
		return ResponseEntity.ok(projects);
	}

	@GetMapping("/{id}")
	@RolesAllowed({"admin", "user"})
	public ResponseEntity<Project> getProjectById(@PathVariable("id") @Pattern(regexp = "^PROJ_\\d{5}$") String projectId) {
		Project project = projectService.getProjectById(projectId);
		return ResponseEntity.ok(project);
	}
}
