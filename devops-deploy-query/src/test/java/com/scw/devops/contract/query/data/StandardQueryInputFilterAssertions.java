package com.scw.devops.contract.query.data;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;

public class StandardQueryInputFilterAssertions {

	public static void assertValues( final StandardQueryInputFilter inputDefinitionFilter, final Boolean includePreview, final String name,
									 final String version, final String validity, final String clazz ) {
		MatcherAssert.assertThat( inputDefinitionFilter.includePreview.get(), CoreMatchers.is( includePreview ) );
		MatcherAssert.assertThat( inputDefinitionFilter.name.get(), CoreMatchers.is( name ) );
		MatcherAssert.assertThat( inputDefinitionFilter.version.get(), CoreMatchers.is( version ) );
		MatcherAssert.assertThat( inputDefinitionFilter.validity.get(), CoreMatchers.is( validity ) );
		MatcherAssert.assertThat( inputDefinitionFilter.productClass.get(), CoreMatchers.is( clazz ) );
	}

}
