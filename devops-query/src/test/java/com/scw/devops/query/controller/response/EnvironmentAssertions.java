package com.scw.devops.query.controller.response;

import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;

import com.scw.devops.query.controller.response.Environment;

public class EnvironmentAssertions {

	public static void assertEnvironmentName(Environment env, String expectedName) {
		MatcherAssert.assertThat(env.name, is(expectedName));
	}
}
