package de.cronn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ImmutableBuilder
public class Company {
	private String name;
	private List<Department> departments;

	Company() {
	}

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

	@ImmutableBuilderSetter
	public void setName(String name) {
		this.name = name;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	@ImmutableBuilderSetter
	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
}
