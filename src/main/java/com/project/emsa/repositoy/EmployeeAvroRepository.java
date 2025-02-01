package com.project.emsa.repositoy;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.emsa.entity.EmployeeAvro;


public interface EmployeeAvroRepository extends JpaRepository<EmployeeAvro, Integer> {

}
