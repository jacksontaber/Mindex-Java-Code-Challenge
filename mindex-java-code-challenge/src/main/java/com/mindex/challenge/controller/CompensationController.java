package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.request.CompensationRequest;
import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
	private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

	@Autowired
	private CompensationService compensationService;

	@PostMapping("/compensation")
	public Compensation createCompensation(@RequestBody CompensationRequest compensationRequest) {
		LOG.debug("Received create compensation request");

		return compensationService.create(compensationRequest);
	}

	@GetMapping("/compensation/read/{id}")
	public Compensation readCompensation(@PathVariable String id) {
		LOG.debug("Received compensation request for employee id :: [{}]", id);

		return compensationService.read(id);
	}
}
