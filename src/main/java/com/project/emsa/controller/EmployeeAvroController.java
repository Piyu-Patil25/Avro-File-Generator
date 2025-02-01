package com.project.emsa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.emsa.entity.EmployeeAvro;
import com.project.emsa.service.EmployeeAvroService;

@Controller
public class EmployeeAvroController {

	@Autowired
	private EmployeeAvroService avroService;
	
	public EmployeeAvroController(EmployeeAvroService avroService) {
		super();
		this.avroService= avroService;
	}

	@GetMapping("/employees")
	public String listEmployees(Model model) {
		model.addAttribute("employees", avroService.getAllEmployees());
		return "employees";
	}
	
	@GetMapping("/employees/new")
	public String createEmployeeForm(Model model) {
		
		//create employee obj to hold employ data 
		EmployeeAvro employeeAvro = new EmployeeAvro();
		model.addAttribute("employeeAvro", employeeAvro);
		return "create_employee";
	}
	
	@PostMapping("/employees")
	public String saveEmployee(@ModelAttribute("employeeAvro") EmployeeAvro employeeAvro) {
		avroService.saveEmployee(employeeAvro);
		return "redirect:/employees";
	}
	
	@GetMapping("/employees/edit/{id}")
	public String editEmployeeForm(@PathVariable Integer id, Model model) {
		model.addAttribute("employeeAvro", avroService.getEmployeeById(id));
		return "edit_employee";
	}
	
	@PostMapping("/employees/{id}")
	public String updateEmployee(@PathVariable Integer id, 
					@ModelAttribute("employeeAvro") EmployeeAvro employeeAvro, 
					Model model) {

		//get employee data from table by id
		EmployeeAvro existingEmployee = avroService.getEmployeeById(id);
		existingEmployee.setId(id);
		existingEmployee.setFirstName(employeeAvro.getFirstName());
		existingEmployee.setLastName(employeeAvro.getLastName());
		existingEmployee.setEmail(employeeAvro.getEmail());
		
		//save updated employee obj
		avroService.updateEmployeeById(existingEmployee);
		return "redirect:/employees";
	}
	
	@GetMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable Integer id) {
		avroService.deleteEmployeeById(id);
		return "redirect:/employees";
	}
	
	
}
