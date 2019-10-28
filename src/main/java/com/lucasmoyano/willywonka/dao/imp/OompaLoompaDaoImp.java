package com.lucasmoyano.willywonka.dao.imp;

import com.lucasmoyano.willywonka.dao.OompaLoompaDao;
import com.lucasmoyano.willywonka.entity.OompaLoompa;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class OompaLoompaDaoImp extends CrudDaoImp<OompaLoompa> implements OompaLoompaDao {

	public OompaLoompaDaoImp() {
		super(OompaLoompa.class);
	}
}
