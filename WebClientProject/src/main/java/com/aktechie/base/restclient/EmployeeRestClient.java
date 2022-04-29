package com.aktechie.base.restclient;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.aktechie.base.bean.Employee;

public class EmployeeRestClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestClient.class);
	private WebClient webClient;

	public EmployeeRestClient(WebClient webClient) {
		this.webClient = webClient;
	}

	// http://localhost:8081/employee/all
	public List<Employee> retreiveAllEmployee() {
		return webClient.get().uri("/all").retrieve().bodyToFlux(Employee.class).collectList().block();
	}

	// http://localhost:8081/employee/employee/101
	public Employee retreiveOneEmployee(int employeeId) {
		try {
			return webClient.get().uri("/employee/{id}", employeeId).retrieve().bodyToMono(Employee.class).block();
		} catch (WebClientResponseException wex) {
			LOGGER.error("Error while getting response {}", wex.getResponseBodyAsString());
			//LOGGER.error("Web Client Error {}", wex.toString());
			throw wex;
		} catch (Exception e) {
			LOGGER.error("Other exception is {}", e.toString());
			throw e;
		}
	}

	public Employee retreiveOneEmployeeByName(String employeeName) {
		try {
			String uri = UriComponentsBuilder.fromUriString("/employee").queryParam("name", employeeName).build()
					.toString();
			return webClient.get().uri(uri).retrieve().bodyToMono(Employee.class).block();
		} catch (WebClientResponseException wex) {
			LOGGER.error("Error while getting response {}", wex.getResponseBodyAsString());
			//LOGGER.error("Web Client Error {}", wex.toString());
			throw wex;
		} catch (Exception e) {
			LOGGER.error("Other exception is {}", e.toString());
			throw e;
		}
	}
	
	//http://localhost:8081/employee/add
	public Employee addEmployee(Employee employee) {
		try {
			return webClient.post().uri("/add")
					.bodyValue(employee)
					.retrieve()
					.bodyToMono(Employee.class)
					.block();					
		} catch (WebClientResponseException wex) {
			LOGGER.error("Error while posting response {}", wex.getResponseBodyAsString());
			//LOGGER.error("Web Client Error {}", wex.toString());
			throw wex;
		} catch (Exception e) {
			LOGGER.error("Other exception is {}", e.toString());
			throw e;
		}
	}
	//http://localhost:8081/employee/update/{id}
	public Employee updateEmployee(int id,Employee employee) {
		try {
			return webClient.put().uri("/update/{id}",id)
					.bodyValue(employee)
					.retrieve()
					.bodyToMono(Employee.class)
					.block();					
		} catch (WebClientResponseException wex) {
			LOGGER.error("Error while putting response {}", wex.getResponseBodyAsString());
			//LOGGER.error("Web Client Error {}", wex.toString());
			throw wex;
		} catch (Exception e) {
			LOGGER.error("Other exception is {}", e.toString());
			throw e;
		}
	}
	//http://localhost:8081/employee/delete/104
		public String deleteEmployee(int id) {
		try {
			return  webClient.delete().uri("/delete/{id}",id)
			 .retrieve()
			 .bodyToMono(String.class)
			 .block();
		} catch (WebClientResponseException wex) {
			LOGGER.error("Error while putting response {}", wex.getResponseBodyAsString());
			//LOGGER.error("Web Client Error {}", wex.toString());
			throw wex;
		} catch (Exception e) {
			LOGGER.error("Other exception is {}", e.toString());
			throw e;
		}
	}
}
