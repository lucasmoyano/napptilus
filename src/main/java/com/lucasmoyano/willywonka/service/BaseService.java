package com.lucasmoyano.willywonka.service;

import com.lucasmoyano.willywonka.dao.CrudDao;
import com.lucasmoyano.willywonka.utils.PageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseService<TEntity> {

	protected CrudDao<TEntity> dao;
	
	public BaseService(CrudDao<TEntity> dao) {
		this.dao = dao;
	}
	
	public TEntity get(long id) {
		return dao.get(id);
	}
	
	public List<TEntity> getAll(){
		return dao.getAll();
	}

	public synchronized List<TEntity> save(List<TEntity> entity) {
		return dao.save(entity);
	}

	public synchronized TEntity save(TEntity entity) {
		List<TEntity> list = new ArrayList<>();
		list.add(entity);
		dao.save(list);
		return entity;
	}

	public void delete(long[] ids) {
		dao.delete(ids);
	}

	public List<TEntity> search(Map<String, Object> mapValues) {
		return dao.search(mapValues);
	}

	public PageAdapter<TEntity> searchPageable(Map<String, Object> params) {
		return dao.searchPageable(params);
	}
}
