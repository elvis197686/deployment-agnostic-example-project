package com.scw.devops.contract.serialisation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;

public class OutputApplicationDefinitionsMixin {

	@JsonCreator
	public OutputApplicationDefinitionsMixin( @JsonProperty( "apps" ) final List<ApplicationDefinition> applications ) {
		System.out.println( "Wont be called" );
	}
}
