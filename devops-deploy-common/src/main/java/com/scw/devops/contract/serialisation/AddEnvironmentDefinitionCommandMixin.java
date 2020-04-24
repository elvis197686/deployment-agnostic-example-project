package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;

public class AddEnvironmentDefinitionCommandMixin {

	@JsonCreator
	public AddEnvironmentDefinitionCommandMixin( @JsonProperty( "def" ) final EnvironmentDefinition data ) {
		System.out.println( "Wont be called" );
	}

}
