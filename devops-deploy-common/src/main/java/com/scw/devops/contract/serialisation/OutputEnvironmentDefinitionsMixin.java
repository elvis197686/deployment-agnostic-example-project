package com.scw.devops.contract.serialisation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;

public class OutputEnvironmentDefinitionsMixin {

	@JsonCreator
	public OutputEnvironmentDefinitionsMixin( @JsonProperty( "envs" ) final List<EnvironmentDefinition> environments ) {
		System.out.println( "Wont be called" );
	}

}
