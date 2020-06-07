package com.scw.devops.contract.serialisation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.query.data.ApplicationDef;

public class ReturnApplicationsDefinitionMixin {

	@JsonCreator
	public ReturnApplicationsDefinitionMixin( @JsonProperty( "applicationDefinitions" ) final List<ApplicationDef> apps ) {
		System.out.println( "Wont be called" );
	}

}
