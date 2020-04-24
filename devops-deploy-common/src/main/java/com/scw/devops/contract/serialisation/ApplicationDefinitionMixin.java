package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.DefinitionBase;

public class ApplicationDefinitionMixin {

	@JsonCreator
	public ApplicationDefinitionMixin( @JsonProperty( "base" ) final DefinitionBase base ) {
		System.out.println( "Wont be called" );
	}

}
