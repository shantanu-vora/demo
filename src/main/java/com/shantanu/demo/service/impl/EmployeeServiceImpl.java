package com.shantanu.demo.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shantanu.demo.entity.Employee;
import com.shantanu.demo.entity.Project;
import com.shantanu.demo.repository.EmployeeRepository;
import com.shantanu.demo.repository.ProjectRepository;
import com.shantanu.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ProjectRepository projectRepository;

//	@PostConstruct
//	public void initializeEmployeeTable() {
//		employeeRepository.saveAll(
//			Stream.of(
//				new Employee("sarathi", 20000.0),
//				new Employee("pranaya", 55000.0),
//				new Employee("puneet", 120000.0),
//				new Employee("ravi", 40000.0)
//			).collect(Collectors.toList()));
//	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(String employeeId) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

		if(optionalEmployee.isPresent()) {
			return optionalEmployee.get();
		} else {
//			throw new CustomException("Employee with employee id " + employeeId + " does not exist");
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Employee saveEmployee(ObjectNode jsonObject) {
		Employee employee = new Employee();
		employee.setEmail(jsonObject.get("email").asText());
		setEmployeeFields(employee, jsonObject);
		List<Project> projects = getProjectList(jsonObject);
		employee.setProjects(projects);
		Employee existingEmployee = employeeRepository.findEmployeeByEmail(employee.getEmail());

		if(existingEmployee == null) {
			return employeeRepository.save(employee);
		} else {
//			throw new CustomException("Product name already exists in the Database");
			throw new DataIntegrityViolationException("Employee already exists. Enter a unique employee email");
		}
	}


	@Override
	public Employee updateEmployee(String id, ObjectNode jsonObject) {
		Employee savedEmployee = getEmployeeById(id);
		setEmployeeFields(savedEmployee, jsonObject);
		List<Project> projects = getProjectList(jsonObject);
		savedEmployee.setProjects(projects);
//		Product existingProduct = productRepository.findProductByName(savedProduct.getName());

//		if(existingProduct == null) {
		return employeeRepository.save(savedEmployee);
//		} else {
//			throw new CustomException("Product name already exists in the Database");
//			throw new DataIntegrityViolationException("Product name already exists. Enter a unique product name");
//		}
	}

	@Override
	public void deleteEmployee(String id) {
		Employee employee = this.getEmployeeById(id);
		employeeRepository.delete(employee);
	}

	private void setEmployeeFields(Employee employee, ObjectNode jsonObject) {
		employee.setName(jsonObject.get("name").asText());

		employee.setSalary(jsonObject.get("salary").asDouble());
	}

	private List<Project> getProjectList(ObjectNode jsonObject) {
		List<Project> projects = new ArrayList<>();
		jsonObject.get("projects").forEach(jsonNode -> {
			String projectTitle = jsonNode.asText();
			if(!projectTitle.equals("")) {
				Project existingProject = projectRepository.findProjectByTitle(projectTitle);
				if(existingProject == null) {
					Project project = new Project();
					project.setTitle(projectTitle);
					projects.add(project);
				} else {
					projects.add(existingProject);
				}
			}
		});
		return projects;
	}

}
