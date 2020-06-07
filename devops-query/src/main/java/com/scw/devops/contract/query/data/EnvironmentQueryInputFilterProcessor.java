package com.scw.devops.contract.query.data;

import java.util.Optional;

public class EnvironmentQueryInputFilterProcessor {

	public static Optional<String> getWantedName( final EnvironmentQueryInputFilter queryFilter ) {
		return queryFilter.name;
	}

	public static Optional<Boolean> isAutoStartWanted( final EnvironmentQueryInputFilter queryFilter ) {
		return queryFilter.autoStart;
	}

}
