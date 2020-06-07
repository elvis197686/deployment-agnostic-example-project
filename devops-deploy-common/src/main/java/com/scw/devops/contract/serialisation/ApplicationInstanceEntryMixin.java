package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.domain.projectversion.ProjectVersion;

public class ApplicationInstanceEntryMixin {

	@JsonCreator
	public ApplicationInstanceEntryMixin( @JsonProperty( "name" ) final String name, @JsonProperty( "repoName" ) final String repoName,
										  @JsonProperty( "version" ) final ProjectVersion version ) {
		System.out.println( "Wont be called" );
	}

}
