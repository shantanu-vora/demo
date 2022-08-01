package com.shantanu.demo.service.impl;

import com.shantanu.demo.entity.Project;
import com.shantanu.demo.repository.ProjectRepository;
import com.shantanu.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public Project getProjectById(String projectId) {
		Optional<Project> optionalProject = projectRepository.findById(projectId);

		if(optionalProject.isPresent()) {
			return optionalProject.get();
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	@Override
	public List<Project> getProjectsWithCostLessThan(Double cost) {
		return projectRepository.findProjectsWithCostLessThan(cost);
	}
}
