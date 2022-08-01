package com.shantanu.demo.service;

import com.shantanu.demo.entity.Project;

import java.util.List;

public interface ProjectService {

	 Project getProjectById(String projectId);
	 List<Project> getAllProjects();
}
