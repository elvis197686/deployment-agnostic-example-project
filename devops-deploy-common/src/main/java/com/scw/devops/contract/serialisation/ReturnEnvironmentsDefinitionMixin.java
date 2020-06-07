package com.scw.devops.contract.serialisation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.Environment;

public class ReturnEnvironmentsDefinitionMixin {

	@JsonCreator
	public ReturnEnvironmentsDefinitionMixin( @JsonProperty( "environmentDefinitions" ) final List<Environment> environments ) {
		System.out.println( "Wont be called" );
	}

}
