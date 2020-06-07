package com.scw.devops.contract.query.data;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;

public class EnvironmentQueryInputFilterAssertions {

	public static void assertValues( final EnvironmentQueryInputFilter inputEnvironmentFilter, final Boolean includePreview, final String name,
									 final String version, final Boolean autoStart ) {
		MatcherAssert.assertThat( inputEnvironmentFilter.includePreview.get(), CoreMatchers.is( includePreview ) );
		MatcherAssert.assertThat( inputEnvironmentFilter.name.get(), CoreMatchers.is( name ) );
		MatcherAssert.assertThat( inputEnvironmentFilter.version.get(), CoreMatchers.is( version ) );
		MatcherAssert.assertThat( inputEnvironmentFilter.autoStart.get(), CoreMatchers.is( autoStart ) );
	}

}
