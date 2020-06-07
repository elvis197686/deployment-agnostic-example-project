package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.ApplicationDef;

public class ReturnApplicationDefinitionMixin {

	@JsonCreator
	public ReturnApplicationDefinitionMixin( @JsonProperty( "applicationDefinition" ) final ApplicationDef app ) {
		System.out.println( "Wont be called" );
	}

}
