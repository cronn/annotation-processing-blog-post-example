package de.cronn;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {
	CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

	@Mapping(target = "groups", source = "departments")
	@Mapping(target = "workers", expression = "java(mapWorkers(company.getAllEmployees()))")
	CompanyDTO mapCompanyToDTO(Company company);

	List<String> map(List<Department> value);

	default String map(Department department) {
		return department.getName();
	}

	List<String> mapWorkers(List<Employee> employee);

	default String map(Employee employee) {
		return employee.getName();
	}
}
