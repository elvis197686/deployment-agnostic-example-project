package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;

public class GetPreviousApplicationDefinitionCommandMixin {

	@JsonCreator
	public GetPreviousApplicationDefinitionCommandMixin( @JsonProperty( "def" ) final ApplicationDefinition def ) {
		System.out.println( "Wont be called" );
	}

}
