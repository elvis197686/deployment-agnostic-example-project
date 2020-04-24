package com.scw.devops.query.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scw.devops.query.controller.request.StandardQueryBody;
import com.scw.devops.query.controller.response.ProductDef;
import com.scw.devops.query.service.DefinitionQuery;

@RestController
public class ProductController {
	private final DefinitionQuery definitionQuery;

	@Autowired
	public ProductController(final DefinitionQuery definitionQuery) {
		this.definitionQuery = definitionQuery;
	}

	@PostMapping(value = "/products", produces = "application/json")
	@ResponseBody
	public Collection<ProductDef> getAllProducts(
			@RequestParam(name = "versionLimit", required = false) final Integer versionLimit,
			@RequestBody final StandardQueryBody filter) {
		return definitionQuery.getProducts(Optional.ofNullable(versionLimit), filter.getInputDefinitionFilter());
	}

}