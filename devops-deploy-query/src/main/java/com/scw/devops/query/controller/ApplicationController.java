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
import com.scw.devops.contract.query.command.getapplications.GetApplicationsCommand;
import com.scw.devops.contract.query.command.getorphanedapplications.GetOrphanedApplicationsSinceRestartCommand;
import com.scw.devops.contract.query.command.getpreviousapplications.GetPreviousApplicationsDefinitionCommand;
import com.scw.devops.contract.query.command.getproductsinapplication.GetProductsInApplicationCommand;
import com.scw.devops.query.controller.request.StandardQueryBody;

@RestController
public class ApplicationController {

	private final DevopsQueryImpl devopsQuery;

	@Autowired
	public ApplicationController( final DevopsQueryImpl devopsQuery ) {
		this.devopsQuery = devopsQuery;
	}

	@PostMapping(value = "/applications", produces = "application/json")
	@ResponseBody
	public DevopsQueryCommandResult getAllApplications(
			@RequestParam(name = "versionLimit", required = false) final Integer versionLimit,
			@RequestBody final StandardQueryBody filter) {
		return devopsQuery.doCommand( new GetApplicationsCommand( Optional.ofNullable( versionLimit ), filter.getInputDefinitionFilter() ) );
	}

	@PostMapping(value = "/applications/{name}/{version}/products", produces = "application/json")
	@ResponseBody
	public DevopsQueryCommandResult getProductsInApplication( @PathVariable( name = "name" ) final String name,
			@PathVariable(name = "version") final String version,
			@RequestParam(name = "versionLimit", required = false) final Integer versionLimit,
			@RequestBody final StandardQueryBody filter) {
		return devopsQuery.doCommand( new GetProductsInApplicationCommand( name,
																		   version,
																		   Optional.ofNullable( versionLimit ),
																		   filter.getInputDefinitionFilter() ) );
	}

	@PostMapping(value = "/applications/{name}/{version}/previous", produces = "application/json")
	@ResponseBody
	public DevopsQueryCommandResult getPreviousApplication( @PathVariable( name = "name" ) final String name,
			@PathVariable(name = "version") final String version) {
		return devopsQuery.doCommand( new GetPreviousApplicationsDefinitionCommand( name, version ) );
	}

	@PostMapping(value = "/applications/orphaned-since-restart", produces = "application/json")
	@ResponseBody
	public DevopsQueryCommandResult getOrphanedApplicationsSinceRestart() {
		return devopsQuery.doCommand( new GetOrphanedApplicationsSinceRestartCommand() );
	}

}