package com.scw.devops.query.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scw.devops.contract.query.DevopsQueryImpl;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.contract.query.command.GetEnvironmentsCommand;
import com.scw.devops.contract.query.command.GetProductsInEnvironmentCommand;
import com.scw.devops.query.controller.request.EnvironmentQueryBody;
import com.scw.devops.query.controller.request.ProductDefinitionQueryBody;

@RestController
public class EnvironmentController {

	private final DevopsQueryImpl devopsQuery;

	@Autowired
	public EnvironmentController( final DevopsQueryImpl devopsQuery ) {
		this.devopsQuery = devopsQuery;
	}

	@PostMapping(value = "/environments", produces = "application/json")
	@ResponseBody
	public DevopsQueryCommandResult getAllEnvironments(
			@RequestParam(name = "versionLimit", required = false) final Integer versionLimit,
			@RequestBody final EnvironmentQueryBody filter) {
		return devopsQuery.doCommand( new GetEnvironmentsCommand( Optional.ofNullable( versionLimit ), filter.getInputFilter() ) );
	}

	@PostMapping(value = "/environments/{name}/{version}/products", produces = "application/json")
	@ResponseBody
	public DevopsQueryCommandResult getProductsInEnvironment( @PathVariable( name = "name" ) final String name,
			@PathVariable(name = "version") final String version,
			@RequestBody final ProductDefinitionQueryBody filter) {
		return devopsQuery
			.doCommand( new GetProductsInEnvironmentCommand( name, version, filter.getInputProductDefinitionFilter().getWantedClass() ) );
	}

}