package de.cronn;

import java.util.ArrayList;
import java.util.List;

public class Department {
	private String name;
	private final List<Employee> employees;

	public Department(String name) {
		this.name = name;
		employees = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}
}
