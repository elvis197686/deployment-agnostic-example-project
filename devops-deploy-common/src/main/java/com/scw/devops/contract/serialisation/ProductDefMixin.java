package com.scw.devops.contract.serialisation;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.ApplicationAlias;
import com.scw.devops.contract.query.data.ConfigurationErrorData;
import com.scw.devops.contract.query.data.EnvironmentDef;

public class ProductDefMixin {

	@JsonCreator
	public ProductDefMixin( @JsonProperty( "name" ) final String name, @JsonProperty( "version" ) final String version,
							@JsonProperty( "productClass" ) final Optional<String> productClass,
							@JsonProperty( "sourceRepository" ) final String sourceRepository,
							@JsonProperty( "deployToEnvironments" ) final List<EnvironmentDef> deployToEnvironments,
							@JsonProperty( "aliasedAppDefs" ) final Optional<List<ApplicationAlias>> aliasedAppDefs,
							@JsonProperty( "error" ) final Optional<ConfigurationErrorData> error ) {
		System.out.println( "Wont be called" );
	}

}
