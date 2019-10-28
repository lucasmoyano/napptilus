package com.lucasmoyano.willywonka.dao;

import com.lucasmoyano.willywonka.utils.PageAdapter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface CrudDao<TEntity> {
    List<TEntity> getAll();
    List<TEntity> getAll(String orderField);
    TEntity get(long id);
    List<TEntity> save(List<TEntity> entity);
    TEntity save(TEntity entity);
    void delete(long[] id);
    void delete(long id);

    boolean exists(long id);
	List<TEntity> search(Map<String, Object> value);
    PageAdapter<TEntity> searchPageable(Map<String, Object> params);
}
