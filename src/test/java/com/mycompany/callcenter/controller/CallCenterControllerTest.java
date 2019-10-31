package com.mycompany.callcenter.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.mycompany.callcenter.Application;
import com.mycompany.callcenter.model.Operator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CallCenterControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private Operator operator;

	private String getRootUrl() {
		return "http://localhost:" + port + "/api/v1";
	}

	@Before
	public void setUp() {
		this.createOperator("one");
	}

	@Test
	public void testGetAllEsmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/employees", HttpMethod.GET, entity,
				String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmployeeById() {
		Operator employee = restTemplate.getForObject(getRootUrl() + "/employees/" + operator.getId(), Operator.class);
		System.out.println(employee.getName());
		Assert.assertNotNull(employee);
	}

	@Test
	public void testUpdatePost() {
		Operator employee = restTemplate.getForObject(getRootUrl() + "/employees/" + operator.getId(), Operator.class);
		employee.setName("operator two");

		restTemplate.put(getRootUrl() + "/employees/" + operator.getId(), employee);

		Operator updatedEmployee = restTemplate.getForObject(getRootUrl() + "/employees/" + operator.getId(),
				Operator.class);
		Assert.assertTrue(employee.getName().equals(updatedEmployee.getName()));
	}

	@Test
	public void testDeletePost() {
		Operator employee = restTemplate.getForObject(getRootUrl() + "/employees/" + operator.getId(), Operator.class);
		Assert.assertNotNull(employee);

		restTemplate.delete(getRootUrl() + "/employees/" + operator.getId());

		try {
			employee = restTemplate.getForObject(getRootUrl() + "/employees/" + operator.getId(), Operator.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

	@Test
	public void testIncomingCalls() {
		this.createOperator("two");
		this.createOperator("three");

		for (int i = 0; i < 100; i++) {
			restTemplate.postForEntity(getRootUrl() + "/incomingcall", "11-222-333" + i, String.class);
		}

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<List> calls = new HttpEntity<List>(null, headers);

		ResponseEntity<List> response = restTemplate.exchange(getRootUrl() + "/waitingcalls", HttpMethod.GET, calls,
				List.class);

		Assert.assertNotNull(response.getBody());

	}

	private void createOperator(String name) {
		Operator employee = new Operator();
		employee.setName("operator " + name);
		operator = restTemplate.postForEntity(getRootUrl() + "/operator", employee, Operator.class).getBody();
	}

}
