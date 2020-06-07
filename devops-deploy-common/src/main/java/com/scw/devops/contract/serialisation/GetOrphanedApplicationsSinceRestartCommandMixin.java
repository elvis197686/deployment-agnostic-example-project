package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;

public class GetOrphanedApplicationsSinceRestartCommandMixin {

	@JsonCreator
	public GetOrphanedApplicationsSinceRestartCommandMixin() {
		System.out.println( "Wont be called" );
	}

}
