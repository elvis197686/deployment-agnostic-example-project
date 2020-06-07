package com.scw.devops.contract.query.data;

import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;

import com.scw.devops.contract.query.data.ApplicationDef;

public class ApplicationAssertions {

	public static void assertApplicationName(final ApplicationDef application, final String expectedName) {
		MatcherAssert.assertThat(application.name, is(expectedName));
	}

	public static void assertApplicationNameAndVersion(final ApplicationDef application, final String expectedName,
			final String expectedVersionString) {
		MatcherAssert.assertThat(application.name, is(expectedName));
		MatcherAssert.assertThat(application.version, is(expectedVersionString));
	}
}
