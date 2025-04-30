package de.cronn;

import java.util.ArrayList;
import java.util.List;

@ImmutableBuilder
public class Department {
	private String name;
	private List<Employee> employees;

	public Department() {
	}

	public Department(String name) {
		this.name = name;
		employees = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	@ImmutableBuilderSetter
	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	@ImmutableBuilderSetter
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
