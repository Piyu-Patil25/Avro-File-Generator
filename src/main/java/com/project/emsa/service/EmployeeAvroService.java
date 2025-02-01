package com.project.emsa.service;

import java.math.BigDecimal;
import java.util.List;

import com.project.emsa.entity.EmployeeAvro;


public interface EmployeeAvroService {

	List<EmployeeAvro> getAllEmployees();
	
	EmployeeAvro saveEmployee(EmployeeAvro employeeAvro);
	
	EmployeeAvro getEmployeeById(Integer id);
	
	EmployeeAvro updateEmployeeById(EmployeeAvro employeeAvro);
	
	void deleteEmployeeById(Integer id);
}
