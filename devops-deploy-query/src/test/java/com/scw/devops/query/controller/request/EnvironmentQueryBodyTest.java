package com.scw.devops.query.controller.request;

import java.io.IOException;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.contract.query.data.EnvironmentQueryInputFilter;
import com.scw.devops.contract.query.data.EnvironmentQueryInputFilterAssertions;
import com.scw.devops.deploy.config.ApplicationConfiguration;

public class EnvironmentQueryBodyTest {

	private static final Boolean INCLUDE_PREVIEW = false;
	private static final String NAME = "env1";
	private static final String VERSION = "0.1.0";
	private static final Boolean AUTO_START = false;

	@Test
	public void shouldDeserialiseFromJson() throws JsonParseException, JsonMappingException, IOException {
		String exampleJson = "{" + "\"inputEnvironmentFilter\": {" + "\"name\": \"" + NAME + "\"," + "\"version\": \""
				+ VERSION + "\"," + "\"autoStart\": \"" + AUTO_START + "\"," + "\"includePreview\": \""
				+ INCLUDE_PREVIEW + "\"" + "}" + "}";
		ObjectMapper objectMapper = ( new ApplicationConfiguration() ).objectMapper();

		System.out
			.println( "JSON: " + objectMapper.writeValueAsString( new EnvironmentQueryBody( new EnvironmentQueryInputFilter( Optional.of( "poo" ),
																															 Optional.empty(),
																															 Optional.empty(),
																															 Optional.empty() ) ) ) );

		EnvironmentQueryBody deserialisedBody = objectMapper.readValue(exampleJson, EnvironmentQueryBody.class);

		MatcherAssert.assertThat(deserialisedBody, CoreMatchers.notNullValue());
		MatcherAssert.assertThat(deserialisedBody.inputEnvironmentFilter, CoreMatchers.notNullValue());
		EnvironmentQueryInputFilterAssertions.assertValues( deserialisedBody.inputEnvironmentFilter, INCLUDE_PREVIEW, NAME, VERSION, AUTO_START );
	}
}
