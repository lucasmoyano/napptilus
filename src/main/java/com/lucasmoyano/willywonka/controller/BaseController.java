package com.lucasmoyano.willywonka.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lucasmoyano.willywonka.utils.PageAdapter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.lucasmoyano.willywonka.service.BaseService;

abstract class BaseController<TEntity> {
	
	BaseService<TEntity> service;
	
	public BaseController(BaseService<TEntity> service) {
		this.service = service;
	}
	
    @RequestMapping("/{id}")
    TEntity get(@RequestHeader HttpHeaders headers, @PathVariable long id) {
    	return service.get(id);
    }
    
    @RequestMapping(value = "", params = {}, method = RequestMethod.GET)
    List<TEntity> getAll(@RequestHeader HttpHeaders headers) {
    	return service.getAll();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    List<TEntity> create(@RequestHeader HttpHeaders headers, @RequestBody List<TEntity> entities) {
    	return service.save(entities);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    TEntity update(@RequestHeader HttpHeaders headers, @RequestBody TEntity entity) {
	    List<TEntity> list = new ArrayList<>();
	    list.add(entity);
    	return service.save(list).get(0);
    }

    @RequestMapping(value = "/{idsWithCommas}", method = RequestMethod.DELETE)
    void delete(@RequestHeader HttpHeaders headers, @PathVariable String idsWithCommas) {
    	String[] strIds = idsWithCommas.split(",");
        long[] ids= new long[strIds.length];
        for (int i = 0; i < strIds.length; i++) {
            ids[i] = Long.parseLong(strIds[i]);
        }
	    service.delete(ids);
    }

    @RequestMapping(method = RequestMethod.GET, value= "/search")
    Object search(@RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
	    if (params.containsKey("page")) {
	        return service.searchPageable(params);
        }
        return service.search(params);
    }
}






