package com.scw.devops.contract.serialisation;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.common.data.EnvironmentProductDefinitionReference;

public class EnvironmentDefinitionMixin {

	@JsonCreator
	public EnvironmentDefinitionMixin( @JsonProperty( "base" ) final DefinitionBase base,
									   @JsonProperty( "products" ) final Collection<EnvironmentProductDefinitionReference> productsInEnvironment ) {
		System.out.println( "Wont be called" );
	}

}
