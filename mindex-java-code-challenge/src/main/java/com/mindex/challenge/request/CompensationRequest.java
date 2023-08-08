package com.mindex.challenge.request;

/**
 * Class for creating Compensations
 * 
 * @author Jackson Taber
 *
 */

public class CompensationRequest {
	private String employeeId;
	private Double salary;
	private String effectiveDate;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}
