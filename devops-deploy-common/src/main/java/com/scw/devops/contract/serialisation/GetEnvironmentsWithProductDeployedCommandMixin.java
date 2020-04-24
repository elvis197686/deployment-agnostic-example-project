package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ProductDefinition;

public class GetEnvironmentsWithProductDeployedCommandMixin {

	@JsonCreator
	public GetEnvironmentsWithProductDeployedCommandMixin( @JsonProperty( "def" ) final ProductDefinition def ) {
		System.out.println( "Wont be called" );
	}

}
