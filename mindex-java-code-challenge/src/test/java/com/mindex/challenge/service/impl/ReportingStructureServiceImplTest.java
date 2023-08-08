package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

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
public class ReportingStructureServiceImplTest {
	private String reportingUrl;

	@Autowired
	private ReportingStructureService reportingStructureService;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		reportingUrl = "http://localhost:" + port + "/reportingStructure/{id}";
	}

	@Test
	public void testReportingStructure() {
		String employeeID = "16a596ae-edd3-4847-99fe-c4518e82c86f";

		// Read check
		ReportingStructure reportingStructure = restTemplate.getForEntity(reportingUrl, ReportingStructure.class, employeeID).getBody();

		//Employee should not be null
		assertNotNull(reportingStructure.getEmployee().getEmployeeId()); 

		//Employee should have 4 direct reports
		assertEquals(reportingStructure.getNumberOfReports(), 4);

	}

}
