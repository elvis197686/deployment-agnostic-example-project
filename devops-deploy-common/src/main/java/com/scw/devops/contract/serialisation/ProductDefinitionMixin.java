package com.scw.devops.contract.serialisation;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntry;
import com.scw.devops.contract.store.common.data.DefinitionBase;

public class ProductDefinitionMixin {

	@JsonCreator
	public ProductDefinitionMixin( @JsonProperty( "base" ) final DefinitionBase base,
								   @JsonProperty( "apps" ) final Collection<ApplicationInstanceEntry> applicationsInProduct ) {
		System.out.println( "Wont be called" );
	}

}
