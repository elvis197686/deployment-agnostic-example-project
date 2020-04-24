package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectVersionMixin {

	@JsonCreator
	public ProjectVersionMixin( @JsonProperty( "version" ) final String version, @JsonProperty( "isPreview" ) final boolean isPreview ) {
		System.out.println( "Wont be called" );
	}

}
