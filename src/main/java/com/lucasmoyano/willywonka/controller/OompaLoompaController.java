package com.lucasmoyano.willywonka.controller;

import com.lucasmoyano.willywonka.entity.OompaLoompa;
import com.lucasmoyano.willywonka.service.OompaLoompaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/oompaloompa")
public class OompaLoompaController extends BaseController<OompaLoompa> {

	@Autowired	
	public OompaLoompaController(OompaLoompaService service) {
		super(service);
	}
}
