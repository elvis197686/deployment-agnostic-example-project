package com.scw.devops.contract.serialisation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.query.data.ApplicationInstance;

public class OutputApplicationInstancesMixin {

	@JsonCreator
	public OutputApplicationInstancesMixin( @JsonProperty( "apps" ) final List<ApplicationInstance> application ) {
		System.out.println( "Wont be called" );
	}

}
