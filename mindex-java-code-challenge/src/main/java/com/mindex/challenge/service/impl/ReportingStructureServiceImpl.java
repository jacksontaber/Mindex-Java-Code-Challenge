package com.mindex.challenge.service.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.List;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

	@Autowired
	private EmployeeService employeeService;

	@Override
	public ReportingStructure read(String id) throws RuntimeException{
		LOG.debug("Creating reporting structure with employee id [{}]", id);

		Employee employee = employeeService.read(id);

		if (employee == null) {
			throw new RuntimeException("Cannot find employee: " + id);
		}

		// Get the initial direct reports from the given employee
		List<Employee> initialReports = employee.getDirectReports();

		// If the given employee does not have any direct reports
		if(initialReports == null || initialReports.isEmpty()) {
			return new ReportingStructure(employee, 0);
		}

		//Create a reports queue to be looped over 
		Queue<Employee> reportsQueue = new LinkedList<Employee>();
		reportsQueue.addAll(initialReports);

		//Create a Set of total reports for the count
		Set<Employee> totalReports = new HashSet<Employee>();
		totalReports.addAll(initialReports);

		//Loop over reportsQueue until it has gone through every report
		while(!reportsQueue.isEmpty()) {

			//Get and remove the next report in the reportsQueue
			String employeeId = reportsQueue.poll().getEmployeeId();

			Employee tempEmployee = employeeService.read(employeeId); 

			List<Employee> reports = tempEmployee.getDirectReports();

			//Add all of the reports of the current employee to the Queue and Set
			if(reports != null && !reports.isEmpty()) {
				reportsQueue.addAll(reports);
				totalReports.addAll(reports);
			}
		}

		return new ReportingStructure(employee, totalReports.size());
	}

}
