package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigurationErrorDataMixin {

	@JsonCreator
	public ConfigurationErrorDataMixin( @JsonProperty( "message" ) final String message ) {
		System.out.println( "Wont be called" );
	}

}
