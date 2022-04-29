package com.aktechie.base.restclient;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.aktechie.base.bean.Employee;

class EmployeeRestClientTest {

	private static final String baseUrl = "http://localhost:8081/employee";
	private WebClient webClient = WebClient.create(baseUrl);

	EmployeeRestClient empRestClient = new EmployeeRestClient(webClient);

	@Test
	void retreiveAllEmployee_test() {
		List<Employee> employeeList = empRestClient.retreiveAllEmployee();
		assertTrue(employeeList.size() > 0);
	}

	@Test
	void retreiveOneEmployee_test() {
		int employeeId=101;
		Employee employee=empRestClient.retreiveOneEmployee(employeeId);
		assertEquals("Employee1",employee.getEmployeeName());
	}
	
	@Test
	void retreiveOneEmployee_test_not_found() {
		int employeeId=105;
		Assertions.assertThrows(WebClientResponseException.class,()-> empRestClient.retreiveOneEmployee(employeeId));
		
	}
	@Test
	void retreiveOneEmployeeByName_test() {
		String name="Employee1";
		Employee employee=empRestClient.retreiveOneEmployeeByName(name);
		assertEquals("Employee1",employee.getEmployeeName());
	}
	
	@Test
	void retreiveOneEmployeeByName_test_not_found() {
		String name="Employee5";
		Assertions.assertThrows(WebClientResponseException.class,()-> empRestClient.retreiveOneEmployeeByName(name));
	
	}
	
	@Test
	void addEmployee_test() {
		Employee emp4=new Employee(104,"Employee4");
		
		Employee employee=empRestClient.addEmployee(emp4);
		assertEquals("Employee4",employee.getEmployeeName());
	}
	
	@Test
	void addEmployee_test_exception() {
		Employee emp=new Employee(105,null);
		Assertions.assertThrows(WebClientResponseException.class,()-> empRestClient.addEmployee(emp));
	
	}

	@Test
	void updateEmployee_test() {
		Employee emp4=new Employee(104,"Employee9");
		Employee employee=empRestClient.updateEmployee(104,emp4);
		assertEquals("Employee9",employee.getEmployeeName());
	}

	@Test
	void updateEmployee_exception() {
		int id=109;
		Employee emp4=new Employee(id,"Employee99");
		Assertions.assertThrows(WebClientResponseException.class,()-> empRestClient.updateEmployee(id,emp4));
	}
	
	@Test
	void deleteEmployee_test() {
		Employee emp5=new Employee(105,"Employee5");
		empRestClient.addEmployee(emp5);
		String value=empRestClient.deleteEmployee(105);
		assertEquals("employee deleted successfully", value);
	}
	
	@Test
	void deleteEmployee_exception() {
		int id=105;
		Assertions.assertThrows(WebClientResponseException.class,()-> empRestClient.deleteEmployee(id));
	}
}
