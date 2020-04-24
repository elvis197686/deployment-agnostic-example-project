package com.scw.devops.query.controller.request;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.query.controller.request.StandardQueryBody;

public class StandardQueryBodyTest {

	private static final Boolean INCLUDE_PREVIEW = false;
	private static final String NAME = "env1";
	private static final String VERSION = "0.1.0";
	private static final String VALIDITY = "ALL";
	private static final String CLASS = "aClass";

	@Test
	public void shouldDeserialiseFromJson() throws JsonParseException, JsonMappingException, IOException {
		String exampleJson = "{" + "\"inputDefinitionFilter\": {" + "\"name\": \"" + NAME + "\"," + "\"version\": \""
				+ VERSION + "\"," + "\"validity\": \"" + VALIDITY + "\"," + "\"productClass\": \"" + CLASS + "\","
				+ "\"includePreview\": \"" + INCLUDE_PREVIEW + "\"" + "}" + "}";
		ObjectMapper objectMapper = new ObjectMapper();
		StandardQueryBody deserialisedBody = objectMapper.readValue(exampleJson, StandardQueryBody.class);

		MatcherAssert.assertThat(deserialisedBody, CoreMatchers.notNullValue());
		MatcherAssert.assertThat(deserialisedBody.inputDefinitionFilter, CoreMatchers.notNullValue());
		MatcherAssert.assertThat(deserialisedBody.inputDefinitionFilter.includePreview,
				CoreMatchers.is(INCLUDE_PREVIEW));
		MatcherAssert.assertThat(deserialisedBody.inputDefinitionFilter.name, CoreMatchers.is(NAME));
		MatcherAssert.assertThat(deserialisedBody.inputDefinitionFilter.version, CoreMatchers.is(VERSION));
		MatcherAssert.assertThat(deserialisedBody.inputDefinitionFilter.validity, CoreMatchers.is(VALIDITY));
		MatcherAssert.assertThat(deserialisedBody.inputDefinitionFilter.productClass, CoreMatchers.is(CLASS));
	}
}
