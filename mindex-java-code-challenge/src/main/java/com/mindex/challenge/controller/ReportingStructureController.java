package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportingStructureController {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

	@Autowired
	private ReportingStructureService reportingStructureService;

	@GetMapping("/reportingStructure/{id}")
	public ReportingStructure read(@PathVariable String id) throws Exception {
		LOG.debug("Received reporting structure for Employee with employee id [{}]", id);
		return reportingStructureService.read(id);
	}
}
