package com.scw.devops.query.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scw.devops.query.controller.request.EnvironmentQueryBody;
import com.scw.devops.query.controller.request.ProductDefinitionQueryBody;
import com.scw.devops.query.controller.response.Environment;
import com.scw.devops.query.controller.response.ProductDef;
import com.scw.devops.query.service.DefinitionQuery;

@RestController
public class EnvironmentController {
	private final DefinitionQuery definitionQuery;

	@Autowired
	public EnvironmentController(final DefinitionQuery definitionQuery) {
		this.definitionQuery = definitionQuery;
	}

	@PostMapping(value = "/environments", produces = "application/json")
	@ResponseBody
	public Collection<Environment> getAllEnvironments(
			@RequestParam(name = "versionLimit", required = false) final Integer versionLimit,
			@RequestBody final EnvironmentQueryBody filter) {
		return definitionQuery.getEnvironments(Optional.ofNullable(versionLimit), filter.getInputFilter());
	}

	@PostMapping(value = "/environments/{name}/{version}/products", produces = "application/json")
	@ResponseBody
	public Collection<ProductDef> getProductsInEnvironment(@PathVariable(name = "name") final String name,
			@PathVariable(name = "version") final String version,
			@RequestBody final ProductDefinitionQueryBody filter) {
		return definitionQuery.getProductsInEnvironment(name, version,
				filter.getInputProductDefinitionFilter().getWantedClass());
	}

}