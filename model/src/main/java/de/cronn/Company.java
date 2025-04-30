package de.cronn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Company {
	private String name;
	private final List<Department> departments;

	public Company(String name) {
		this.name = name;
		departments = new ArrayList<>();
	}

	public List<Employee> getAllEmployees() {
		return departments
			.stream()
			.map(Department::getEmployees)
			.flatMap(Collection::stream)
			.toList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void addDepartment(Department department) {
		this.departments.add(department);
	}
}
