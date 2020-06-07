package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OutputVoidMixin {

	@JsonCreator
	public OutputVoidMixin( @JsonProperty( "dummyBoolean" ) final boolean dummyBoolean ) {
		System.out.println( "Wont be called" );
	}

}
