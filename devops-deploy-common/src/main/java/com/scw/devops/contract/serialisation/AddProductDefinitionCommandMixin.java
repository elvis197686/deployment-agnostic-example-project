package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ProductDefinition;

public class AddProductDefinitionCommandMixin {

	@JsonCreator
	public AddProductDefinitionCommandMixin( @JsonProperty( "def" ) final ProductDefinition def ) {
		System.out.println( "Wont be called" );
	}

}
