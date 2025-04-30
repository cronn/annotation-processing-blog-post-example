package de.cronn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ImmutableCompanyBuilderTest {

	@Test
	void testImmutableCompanyBuilder() throws Exception {
		ImmutableCompanyBuilder.ImmutableCompany immutableCompany = createDummyImmutableCompany();

		Assertions.assertThat(immutableCompany.getName()).isEqualTo("company");
	}

	@Test
	void testCreatedCompanyIsImmutable() throws Exception {
		ImmutableCompanyBuilder.ImmutableCompany immutableCompany = createDummyImmutableCompany();

		Assertions.assertThatThrownBy(() -> immutableCompany.setName("wrong"))
			.isInstanceOf(RuntimeException.class);
	}

	private static ImmutableCompanyBuilder.ImmutableCompany createDummyImmutableCompany() {
		return new ImmutableCompanyBuilder()
			.setName("company")
			.build();
	}
}
