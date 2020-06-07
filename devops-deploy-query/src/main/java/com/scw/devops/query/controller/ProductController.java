package com.scw.devops.query.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scw.devops.contract.query.DevopsQueryImpl;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.contract.query.command.GetProductsCommand;
import com.scw.devops.query.controller.request.StandardQueryBody;

@RestController
public class ProductController {

	private final DevopsQueryImpl devopsQuery;

	@Autowired
	public ProductController( final DevopsQueryImpl devopsQuery ) {
		this.devopsQuery = devopsQuery;
	}

	@PostMapping(value = "/products", produces = "application/json")
	@ResponseBody
	public DevopsQueryCommandResult getAllProducts(
			@RequestParam(name = "versionLimit", required = false) final Integer versionLimit,
			@RequestBody final StandardQueryBody filter) {
		return devopsQuery.doCommand( new GetProductsCommand( Optional.ofNullable( versionLimit ), filter.getInputDefinitionFilter() ) );
	}

}