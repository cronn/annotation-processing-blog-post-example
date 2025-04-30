package de.cronn;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompanyDTO {
	private String name;
	private List<String> groups;
	private List<String> workers;
}
