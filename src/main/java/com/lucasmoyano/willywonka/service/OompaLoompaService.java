package com.lucasmoyano.willywonka.service;

import com.lucasmoyano.willywonka.dao.OompaLoompaDao;
import com.lucasmoyano.willywonka.entity.OompaLoompa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OompaLoompaService extends BaseService<OompaLoompa> {
	
	@Autowired
	public OompaLoompaService(OompaLoompaDao dao) {
		super(dao);
	}
	
}
