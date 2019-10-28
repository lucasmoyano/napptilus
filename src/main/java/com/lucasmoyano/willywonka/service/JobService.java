package com.lucasmoyano.willywonka.service;

import com.lucasmoyano.willywonka.dao.JobDao;
import com.lucasmoyano.willywonka.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService extends BaseService<Job> {
	
	@Autowired
	public JobService(JobDao dao) {
		super(dao);
	}
	
}
