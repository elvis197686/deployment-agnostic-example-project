package com.scw.devops.contract.query.data;

public class FilterDefaults {

	public static EnvironmentQueryInputFilter makeDefaultEnvironmentQueryInputFilter() {
		return new EnvironmentQueryInputFilter( null, null, null, null );
	}

	public static StandardQueryInputFilter makeDefaultStandardQueryInputFilter() {
		return new StandardQueryInputFilter( null, null, null, null, null );
	}

}

