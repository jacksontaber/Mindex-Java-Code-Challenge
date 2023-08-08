package com.mindex.challenge.service.impl;


import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.request.CompensationRequest;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CompensationServiceImpl implements CompensationService {
	private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

	@Autowired
	private CompensationRepository compensationRepository;

	@Autowired
	private EmployeeService employeeService;

	@Override
	public Compensation create(CompensationRequest compensationRequest) {
		LOG.debug("Creating compensation [{}]", compensationRequest);

		Employee employee = employeeService.read(compensationRequest.getEmployeeId());
		BigDecimal salary = new BigDecimal(compensationRequest.getSalary());
		LocalDate effectiveDate = LocalDate.parse(compensationRequest.getEffectiveDate());

		if(employee == null) {
			throw new RuntimeException("Invalid employee: " + compensationRequest.getEmployeeId());
		}

		if(salary == null || salary.compareTo(BigDecimal.ZERO) == -1) {
			throw new RuntimeException("Invalid salary: " + compensationRequest.getSalary());
		}

		if(effectiveDate == null) {
			throw new RuntimeException("Invalid effective date: " + compensationRequest.getEffectiveDate());
		}

		Compensation compensation = new Compensation(
				employee,
				salary,
				effectiveDate
				);

		return compensationRepository.insert(compensation);

	}

	@Override
	public Compensation read(String employeeId) {
		LOG.debug("Read compensation for employee [{}]", employeeId);

		Employee employee = employeeService.read(employeeId);

		//Get today's date
		Date todayDate = new Date();

		//Get List of compensations based on employee and date
		List<Compensation> compensations = compensationRepository.findCompensationsByEmployeeIdAndDate(employee, todayDate);

		//Check if the employee has a valid compensation
		if(compensations != null && compensations.size() > 0) {
			return compensations.get(0);
		}

		return null;
	}
}
