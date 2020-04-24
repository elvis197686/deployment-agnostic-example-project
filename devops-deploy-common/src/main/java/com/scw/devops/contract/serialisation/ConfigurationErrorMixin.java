package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigurationErrorMixin {

	@JsonCreator
	public ConfigurationErrorMixin( @JsonProperty( "message" ) final String message ) {
		System.out.println( "Wont be called" );
	}

}
