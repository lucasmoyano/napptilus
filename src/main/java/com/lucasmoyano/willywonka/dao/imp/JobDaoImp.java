package com.lucasmoyano.willywonka.dao.imp;

import com.lucasmoyano.willywonka.dao.JobDao;
import com.lucasmoyano.willywonka.entity.Job;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class JobDaoImp extends CrudDaoImp<Job> implements JobDao {

	public JobDaoImp() {
		super(Job.class);
	}
}
