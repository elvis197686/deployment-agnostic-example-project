package com.scw.devops.query.controller.response;

import java.util.Arrays;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.contract.query.data.ConfigurationErrorData;
import com.scw.devops.contract.query.data.Environment;
import com.scw.devops.contract.query.data.EnvironmentDef;
import com.scw.devops.deploy.config.ApplicationConfiguration;

public class EnvironmentTest {

	private static final String A_NAME = "A name";
	private static final String A_VERSION = "0.1.0";
	private static final String AN_ERROR = "An error";
	private static final String ANOTHER_ERROR = "Another error";

	private static final String A_DEFINITION_NAME = "Another name";
	private static final String A_DEFINITION_VERSION = "0.2.0";
	private static final boolean AN_AUTOSTART_SETTING = false;
	private static final String A_SOURCE_REPOSITORY = "http://example/it.git";
	private static final String A_DEFINITION_ERROR = "An def error";

	@Test
	public void shouldSerialiseToJson() throws JsonProcessingException, JSONException {

		JSONObject expectedJson = getExpectedJson();
		ObjectMapper objectMapper = ( new ApplicationConfiguration() ).objectMapper();

		String actualJson = objectMapper.writeValueAsString( getExampleObject() );

		JSONAssert.assertEquals("Expected deserialised json does not match actual json", expectedJson.toString(),
				actualJson, true);
	}

	private Environment getExampleObject() {

		return new Environment(A_NAME, A_VERSION,
								new EnvironmentDef( A_DEFINITION_NAME,
													A_DEFINITION_VERSION,
													Optional.of( AN_AUTOSTART_SETTING ),
													A_SOURCE_REPOSITORY,
													Optional
														.of( new ConfigurationErrorData( A_DEFINITION_ERROR ) ) ),
								Arrays.asList( new ConfigurationErrorData( AN_ERROR ), new ConfigurationErrorData( ANOTHER_ERROR ) ) );
	}

	private JSONObject getExpectedJson() throws JsonProcessingException, JSONException {
		JSONObject expectedDefinitionError = new JSONObject();
		expectedDefinitionError.put("message", A_DEFINITION_ERROR);

		JSONObject expectedDefinition = new JSONObject();
		expectedDefinition.put("name", A_DEFINITION_NAME);
		expectedDefinition.put("version", A_DEFINITION_VERSION);
		expectedDefinition.put("autoStart", AN_AUTOSTART_SETTING);
		expectedDefinition.put("sourceRepository", A_SOURCE_REPOSITORY);
		expectedDefinition.put("error", expectedDefinitionError);

		JSONArray expectedErrors = new JSONArray();
		expectedErrors.put(new JSONObject().put("message", AN_ERROR));
		expectedErrors.put(new JSONObject().put("message", ANOTHER_ERROR));

		JSONObject expectedJson = new JSONObject();
		expectedJson.put("name", A_NAME);
		expectedJson.put("version", A_VERSION);
		expectedJson.put("errors", expectedErrors);
		expectedJson.put("definition", expectedDefinition);

		return expectedJson;
	}
}
