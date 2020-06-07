package com.scw.devops.contract.query.data;

import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.MatcherAssert;

import com.scw.devops.contract.query.data.Environment;

public class EnvironmentAssertions {

	public static void assertEnvironmentName(final Environment env, final String expectedName) {
		MatcherAssert.assertThat(env.name, is(expectedName));
	}
}
