package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ProductDefinition;

public class GetProductApplicationReferencesCommandMixin {

	@JsonCreator
	public GetProductApplicationReferencesCommandMixin( @JsonProperty( "def" ) final ProductDefinition productDefinition ) {
		System.out.println( "Wont be called" );
	}

}
