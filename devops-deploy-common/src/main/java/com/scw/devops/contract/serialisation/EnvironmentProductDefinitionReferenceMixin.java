package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.common.data.ProjectVersion;

public class EnvironmentProductDefinitionReferenceMixin {

	@JsonCreator
	public EnvironmentProductDefinitionReferenceMixin( @JsonProperty( "name" ) final String name,
													   @JsonProperty( "version" ) final ProjectVersion version ) {
		System.out.println( "Wont be called" );
	}

}
