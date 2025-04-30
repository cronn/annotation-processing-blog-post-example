package de.cronn;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CompanyEntityTest {

	@Test
	void testMetaModel() throws Exception {
		assertThat(CompanyEntity_.ID).isEqualTo("id");
		assertThat(CompanyEntity_.NAME).isEqualTo("name");
		assertThat(CompanyEntity_.DEPARTMENTS).isEqualTo("departments");
	}
}
