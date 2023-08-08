package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.request.CompensationRequest;

public interface CompensationService {
	Compensation create(CompensationRequest compensationRequest);
	Compensation read(String employeeId);
}
