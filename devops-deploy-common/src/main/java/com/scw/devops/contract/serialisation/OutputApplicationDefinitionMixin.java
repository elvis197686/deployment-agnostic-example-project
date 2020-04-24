package com.scw.devops.contract.serialisation;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;

public class OutputApplicationDefinitionMixin {

	@JsonCreator
	public OutputApplicationDefinitionMixin( @JsonProperty( "app" ) final Optional<ApplicationDefinition> application ) {
		System.out.println( "Wont be called" );
	}

}
