package com.shantanu.demo.repository;

import com.shantanu.demo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

	Project findProjectByTitle(String title);

	@Query(value = "SELECT p.id, p.title FROM (SELECT p.id, p.title, SUM(e.salary) AS cost" +
		" FROM employee e JOIN employee_project ep ON e.id = ep.employee_id" +
		" JOIN project p ON p.id = ep.project_id GROUP BY project_id HAVING cost <= :cost) AS p", nativeQuery = true)
	List<Project> findProjectsWithCostLessThan(@Param("cost") Double cost);
}
