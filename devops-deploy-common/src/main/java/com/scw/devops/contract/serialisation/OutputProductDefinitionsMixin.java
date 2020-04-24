package com.scw.devops.contract.serialisation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ProductDefinition;

public class OutputProductDefinitionsMixin {

	@JsonCreator
	public OutputProductDefinitionsMixin( @JsonProperty( "prods" ) final List<ProductDefinition> products ) {
		System.out.println( "Wont be called" );
	}

}
