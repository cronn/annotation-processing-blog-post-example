package de.cronn;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class CompanyMapperTest {

	@Test
	void mapCompanyToDTO() {
		Company company = createDummyCompany();

		CompanyDTO companyDTO = CompanyMapper.INSTANCE.mapCompanyToDTO(company);

		CompanyDTO expected = new CompanyDTO();
		expected.setName("company");
		expected.setGroups(List.of("dep no. 1", "dep no. 2"));
		expected.setWorkers(List.of("Alice", "Bob", "Christel", "Daniel"));

		assertThat(companyDTO)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}

	private static Company createDummyCompany() {
		Company company = new Company("company");
		Department department1 = new Department("dep no. 1");
		company.addDepartment(department1);
		department1.addEmployee(new Employee("Alice", Employee.ROLE.DEVELOPER));
		department1.addEmployee(new Employee("Bob", Employee.ROLE.TESTER));
		Department department2 = new Department("dep no. 2");
		company.addDepartment(department2);
		department2.addEmployee(new Employee("Christel", Employee.ROLE.DEVELOPER));
		department2.addEmployee(new Employee("Daniel", Employee.ROLE.ANALYST));
		return company;
	}
}
