package com.example.demo.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.bean.Employee;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {
	private List<Employee> listOfEmployee;
	
	public EmployeeRestController() {
		listOfEmployee=new LinkedList<>();
		Employee e1=new Employee(101, "Employee1");
		Employee e2=new Employee(102, "Employee2");
		listOfEmployee.add(e1);listOfEmployee.add(e2);
	}
	
	@GetMapping("/all")
	public List<Employee> getEmployees() {
		
		return listOfEmployee;
	}

	@GetMapping("/employee/{id}")
	public Employee getOneEmployee(@PathVariable Integer id) {
		try{
			return listOfEmployee.stream().filter(x-> x.getEmployeeId()==id).collect(Collectors.toList()).get(0);
		}catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found"
					);
		}

	}
	@GetMapping("/employee")
	public Employee getOneEmployeeByName(@RequestParam String  name) {
		try{
			return listOfEmployee.stream().filter(x-> x.getEmployeeName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);
		}catch(Exception e) {
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "Entity not found"
					);
		}

	}
	
	@PostMapping("/add")
	public Employee createEmployee(@RequestBody Employee employee) throws Exception {
		try
		{
			if (!employee.getEmployeeName().equals(null))
				{
			listOfEmployee.add(employee);
			return employee;
				}
			else throw new InvalidAttributesException();
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@PutMapping("/update/{id}")
	public Employee modifyEmployee(@RequestBody Employee employee ,@PathVariable Integer id) {
		try {
			Employee e=listOfEmployee.stream().filter(x-> x.getEmployeeId()==id).collect(Collectors.toList()).get(0);
			e.setEmployeeName(employee.getEmployeeName());
			listOfEmployee.add(e);
			return e;
		}catch(Exception e) {
			throw new NoSuchElementException();
		}
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		try{
			Employee e=listOfEmployee.stream().filter(x-> x.getEmployeeId()==id).collect(Collectors.toList()).get(0);			
			listOfEmployee.remove(e);
			return "employee deleted successfully";
		}catch(Exception e) {
			throw new NoSuchElementException();
		}
	}
}
