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

import com.scw.devops.query.controller.request.StandardQueryBody;
import com.scw.devops.query.controller.response.ApplicationDef;
import com.scw.devops.query.controller.response.ProductDef;
import com.scw.devops.query.service.DefinitionQuery;

@RestController
public class ApplicationController {
	private final DefinitionQuery definitionQuery;

	@Autowired
	public ApplicationController(final DefinitionQuery definitionQuery) {
		this.definitionQuery = definitionQuery;
	}

	@PostMapping(value = "/applications", produces = "application/json")
	@ResponseBody
	public Collection<ApplicationDef> getAllApplications(
			@RequestParam(name = "versionLimit", required = false) final Integer versionLimit,
			@RequestBody final StandardQueryBody filter) {
		return definitionQuery.getApplications(Optional.ofNullable(versionLimit), filter.getInputDefinitionFilter());
	}

	@PostMapping(value = "/applications/{name}/{version}/products", produces = "application/json")
	@ResponseBody
	public Collection<ProductDef> getProductsInApplication(@PathVariable(name = "name") final String name,
			@PathVariable(name = "version") final String version,
			@RequestParam(name = "versionLimit", required = false) final Integer versionLimit,
			@RequestBody final StandardQueryBody filter) {
		return definitionQuery.getProductsInApplication(name, version, Optional.ofNullable(versionLimit),
				filter.getInputDefinitionFilter());
	}

	@PostMapping(value = "/applications/{name}/{version}/previous", produces = "application/json")
	@ResponseBody
	public ApplicationDef getPreviousApplication(@PathVariable(name = "name") final String name,
			@PathVariable(name = "version") final String version) {
		return definitionQuery.getPreviousApplicationDefinition(name, version);
	}

	@PostMapping(value = "/applications/orphaned-since-restart", produces = "application/json")
	@ResponseBody
	public Collection<ApplicationDef> getOrphanedApplicationsSinceRestart() {
		return definitionQuery.getOrphanedApplicationDefinitionsSinceRestart();
	}

}