package de.cronn;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CompanyDTOTest {

	@Test
	void testDTO() {
		CompanyDTO companyDTO = CompanyDTO.builder()
			.name("company")
			.groups(List.of("dep no. 1", "dep no. 2"))
			.workers(List.of("Alice", "Bob", "Christel", "Daniel"))
			.build();

		CompanyDTO expected = new CompanyDTO(null, null, null);
		expected.setName("company");
		expected.setGroups(List.of("dep no. 1", "dep no. 2"));
		expected.setWorkers(List.of("Alice", "Bob", "Christel", "Daniel"));

		assertThat(companyDTO)
			.usingRecursiveComparison()
			.isEqualTo(expected);
	}
}
