package com.scw.devops.query.controller.request;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.contract.query.data.StandardQueryInputFilterAssertions;
import com.scw.devops.deploy.config.ApplicationConfiguration;

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
		ObjectMapper objectMapper = ( new ApplicationConfiguration() ).objectMapper();
		StandardQueryBody deserialisedBody = objectMapper.readValue(exampleJson, StandardQueryBody.class);

		MatcherAssert.assertThat(deserialisedBody, CoreMatchers.notNullValue());
		MatcherAssert.assertThat( deserialisedBody.inputDefinitionFilter, CoreMatchers.notNullValue() );
		StandardQueryInputFilterAssertions.assertValues( deserialisedBody.inputDefinitionFilter, INCLUDE_PREVIEW, NAME, VERSION, VALIDITY, CLASS );
	}
}
