package com.lucasmoyano.willywonka.controller;

import com.lucasmoyano.willywonka.entity.Job;
import com.lucasmoyano.willywonka.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/job")
public class JobController extends BaseController<Job> {

	@Autowired	
	public JobController(JobService service) {
		super(service);
	}
}
