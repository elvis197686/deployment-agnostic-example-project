package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;

public class AddApplicationDefinitionCommandMixin {

	@JsonCreator
	public AddApplicationDefinitionCommandMixin( @JsonProperty( "def" ) final ApplicationDefinition def ) {
		System.out.println( "Wont be called" );
	}

}
