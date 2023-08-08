package com.mindex.challenge.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

import com.mindex.challenge.request.CompensationRequest;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

	private String compensationUrl;
	private String readCompensationUrl;

	@Autowired
	private CompensationService compensationService;

	@Autowired
	private EmployeeService employeeService;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		compensationUrl = "http://localhost:" + port + "/compensation";
		readCompensationUrl = "http://localhost:" + port + "/compensation/read/{id}";
	}

	@Test
	public void TestCompensation()
	{
		String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

		Employee employee = employeeService.read(employeeId);

		String date1 = "2022-01-01";
		Double salary1 = 100000.00;

		CompensationRequest compensation1 = new CompensationRequest();
		compensation1.setEmployeeId(employeeId);
		compensation1.setSalary(salary1);
		compensation1.setEffectiveDate(date1);

		// Create check
		Compensation createdCompensation1 = restTemplate.postForEntity(compensationUrl, 
				compensation1, Compensation.class).getBody();

		assertNotNull(createdCompensation1.getEmployee().getEmployeeId());
		assertCompensationRequestEquivalence(compensation1, createdCompensation1);

		// Read check
		Compensation readCompensation1 = restTemplate.getForEntity(readCompensationUrl, 
				Compensation.class, createdCompensation1.getEmployee().getEmployeeId()).getBody();

		assertCompensationEquivalence(createdCompensation1, readCompensation1);

		// Create a new Compensation for the same employee
		String date2 = "2023-01-01";
		Double salary2 = 110000.50;

		CompensationRequest compensation2 = new CompensationRequest();
		compensation2.setEmployeeId(employeeId);
		compensation2.setSalary(salary2);
		compensation2.setEffectiveDate(date2);

		// Create check
		Compensation createdCompensation2 = restTemplate.postForEntity(compensationUrl, 
				compensation2, Compensation.class).getBody();

		assertNotNull(createdCompensation2.getEmployee().getEmployeeId());
		assertCompensationRequestEquivalence(compensation2 ,createdCompensation2);

		// Read check
		Compensation readCompensation2 = restTemplate.getForEntity(readCompensationUrl, 
				Compensation.class, employeeId).getBody();

		assertCompensationEquivalence(createdCompensation2, readCompensation2);

		// Create a new Compensation for the same employee but with a future effective date
		LocalDate tempDate = LocalDate.now();
		int futureYear = tempDate.getYear() + 1;

		String date3 = futureYear + "-01-01";
		Double salary3 = 125000.75;

		CompensationRequest compensation3 = new CompensationRequest();
		compensation3.setEmployeeId(employeeId);
		compensation3.setSalary(salary3);
		compensation3.setEffectiveDate(date3);

		// Create check
		Compensation createdCompensation3 = restTemplate.postForEntity(compensationUrl, 
				compensation3, Compensation.class).getBody();

		assertNotNull(createdCompensation3.getEmployee().getEmployeeId());
		assertCompensationRequestEquivalence(compensation3 ,createdCompensation3);

		// Read check
		Compensation readCompensation3 = restTemplate.getForEntity(readCompensationUrl, 
				Compensation.class, employeeId).getBody();

		// Since compensation3 has a future date it should still be compensation2
		assertCompensationEquivalence(createdCompensation2, readCompensation3);
	}

	private static void assertCompensationRequestEquivalence(CompensationRequest expected, Compensation actual) {
		assertEquals(expected.getEmployeeId(), actual.getEmployee().getEmployeeId());
		assertEquals(new BigDecimal(expected.getSalary()).setScale(2), actual.getSalary());
		assertEquals(LocalDate.parse(expected.getEffectiveDate()), actual.getEffectiveDate());
	}

	private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
		assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
		assertEquals(expected.getSalary() , actual.getSalary());
		assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
	}

}
