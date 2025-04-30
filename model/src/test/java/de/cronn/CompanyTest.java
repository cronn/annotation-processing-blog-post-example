package de.cronn;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CompanyTest {

	@Test
	void getAllEmployees() {
		Company company = createDummyCompany();

		assertThat(company.getAllEmployees())
			.hasSize(4);
	}

	private static Company createDummyCompany() {
		Company company = new Company("company");
		Department department1 = new Department("dep no. 1");
		company.addDepartment(department1);
		department1.addEmployee(new Employee("Alice", Employee.ROLE.DEVELOPER));
		department1.addEmployee(new Employee("Bob", Employee.ROLE.DEVELOPER));
		Department department2 = new Department("dep no. 2");
		company.addDepartment(department2);
		department2.addEmployee(new Employee("Christel", Employee.ROLE.DEVELOPER));
		department2.addEmployee(new Employee("Bob", Employee.ROLE.DEVELOPER));
		return company;
	}
}
