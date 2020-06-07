package com.scw.devops.contract.serialisation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.ProductDef;

public class ReturnProductsDefinitionMixin {

	@JsonCreator
	public ReturnProductsDefinitionMixin( @JsonProperty( "productDefinitions" ) final List<ProductDef> products ) {
		System.out.println( "Wont be called" );
	}

}
