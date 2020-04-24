package com.scw.devops.query.controller.response;

import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;

import com.scw.devops.query.controller.response.ApplicationDef;

public class ApplicationAssertions {

	public static void assertApplicationName(ApplicationDef application, String expectedName) {
		MatcherAssert.assertThat(application.name, is(expectedName));
	}

	public static void assertApplicationNameAndVersion(ApplicationDef application, String expectedName,
			String expectedVersionString) {
		MatcherAssert.assertThat(application.name, is(expectedName));
		MatcherAssert.assertThat(application.version, is(expectedVersionString));
	}
}
