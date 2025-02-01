package com.project.emsa.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.emsa.entity.EmployeeAvro;
import com.project.emsa.repositoy.EmployeeAvroRepository;
import com.project.emsa.service.EmployeeAvroService;

@Service
public class EmployeeAvroServiceImpl implements EmployeeAvroService{
	
	private EmployeeAvroRepository avroRepository;

	public EmployeeAvroServiceImpl(EmployeeAvroRepository avroRepository) {
		super();
		this.avroRepository = avroRepository;
	}
	
	@Override
	public List<EmployeeAvro> getAllEmployees() {
		return avroRepository.findAll();
	}

	@Override
	public EmployeeAvro saveEmployee(EmployeeAvro employeeAvro) {
		return avroRepository.save(employeeAvro);
	}

	@Override
	public EmployeeAvro getEmployeeById(Integer id) {
		return avroRepository.findById(id).get();
	}

	@Override
	public EmployeeAvro updateEmployeeById(EmployeeAvro employeeAvro) {
		return avroRepository.save(employeeAvro);
	}

	@Override
	public void deleteEmployeeById(Integer id) {
		avroRepository.deleteById(id);
	}

}
