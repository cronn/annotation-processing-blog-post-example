package de.cronn;

public class Employee {
	private static int employeeCounter = 1;

	public enum ROLE {
		DEVELOPER,
		TESTER,
		ANALYST
	}

	private String name;
	private final int number;
	private final ROLE role;

	public Employee(String name, ROLE role) {
		this.name = name;
		number = employeeCounter++;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public ROLE getRole() {
		return role;
	}
}
