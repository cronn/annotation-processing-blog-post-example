package de.cronn;

@ImmutableBuilder
public class Employee {
	private static int employeeCounter = 1;

	public enum ROLE {
		DEVELOPER,
		TESTER,
		ANALYST
	}

	private String name;
	private final int number;
	private ROLE role;

	public Employee() {
		number = employeeCounter++;
	}

	public Employee(String name, ROLE role) {
		this();
		this.name = name;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	@ImmutableBuilderSetter
	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public ROLE getRole() {
		return role;
	}

	@ImmutableBuilderSetter
	public void setRole(ROLE role) {
		this.role = role;
	}
}
