package com.scw.devops.contract.query.data;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProductDef {

	final String name;
	final String version;

	final Optional<String> productClass;
	final String sourceRepository;

	final List<EnvironmentDef> deployToEnvironments;

	final Optional<List<ApplicationAlias>> aliasedAppDefs;

	final Optional<ConfigurationErrorData> error;
}
