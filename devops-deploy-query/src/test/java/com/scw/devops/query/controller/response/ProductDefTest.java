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
import com.scw.devops.contract.query.data.ApplicationAlias;
import com.scw.devops.contract.query.data.ApplicationDef;
import com.scw.devops.contract.query.data.ConfigurationErrorData;
import com.scw.devops.contract.query.data.EnvironmentDef;
import com.scw.devops.contract.query.data.ProductDef;
import com.scw.devops.deploy.config.ApplicationConfiguration;

public class ProductDefTest {

	private static final String A_NAME = "A name";
	private static final String A_VERSION = "0.1.0";
	private static final String AN_ERROR = "An error";
	private static final String A_SOURCE_REPOSITORY = "http://example/it.git";

	private static final String A_CLASS_NAME = "aClass";

	private static final String AN_APPLICATION_ALIAS = "appAlias";
	private static final String AN_APPLICATION_NAME = "appName";
	private static final String AN_APPLICATION_VERSION = "0.3.0";
	private static final String AN_APPLICATION_RUNTIME = "JAVA_11";
	private static final String AN_APPLICATION_IMAGE_REF = "abc/gef";

	private static final String A_DEFINITION_NAME = "Another name";

	@Test
	// @Ignore("It is failing to match the Json, even though they appear to be
	// matching")
	public void shouldSerialiseToJson() throws JsonProcessingException, JSONException {

		JSONObject expectedJson = getExpectedJson();
		ObjectMapper objectMapper = ( new ApplicationConfiguration() ).objectMapper();

		String actualJson = objectMapper.writeValueAsString( getExampleObject() );

		JSONAssert.assertEquals("Expected deserialised json does not match actual json", expectedJson.toString(),
				actualJson, false);
	}

	private ProductDef getExampleObject() {

		return new ProductDef( A_NAME,
							   A_VERSION,
							   Optional.of( A_CLASS_NAME ),
							   A_SOURCE_REPOSITORY,
							   Arrays.asList( getExampleEnvironmentObject() ),
							   Optional.of( Arrays.asList( getExampleApplicationObject() ) ),
							   Optional.of( new ConfigurationErrorData( AN_ERROR ) ) );
	}

	private EnvironmentDef getExampleEnvironmentObject() {
		return new EnvironmentDef( A_DEFINITION_NAME, "Any version", Optional.of( true ), "Any source repo", Optional.empty() );
	}

	private ApplicationAlias getExampleApplicationObject() {
		return new ApplicationAlias(AN_APPLICATION_ALIAS, new ApplicationDef(AN_APPLICATION_NAME,
				AN_APPLICATION_VERSION, A_SOURCE_REPOSITORY, AN_APPLICATION_IMAGE_REF, AN_APPLICATION_RUNTIME, null));
	}

	private JSONObject getExpectedJson() throws JsonProcessingException, JSONException {
		JSONObject expectedEnvironmentDefinition = new JSONObject();
		expectedEnvironmentDefinition.put("name", A_DEFINITION_NAME);

		JSONArray expectedEnvironmentDefinitions = new JSONArray();
		expectedEnvironmentDefinitions.put(expectedEnvironmentDefinition);

		JSONObject expectedApplicationDefinition = new JSONObject();
		expectedApplicationDefinition.put("name", AN_APPLICATION_NAME);
		expectedApplicationDefinition.put("version", AN_APPLICATION_VERSION);
		expectedApplicationDefinition.put("sourceRepository", A_SOURCE_REPOSITORY);
		expectedApplicationDefinition.put("imageRepository", AN_APPLICATION_IMAGE_REF);
		expectedApplicationDefinition.put( "applicationRuntime", AN_APPLICATION_RUNTIME );

		JSONObject expectedApplicationAlias = new JSONObject();
		expectedApplicationAlias.put("alias", AN_APPLICATION_ALIAS);
		expectedApplicationAlias.put("definition", expectedApplicationDefinition);

		JSONArray expectedApplicationAliases = new JSONArray();
		expectedApplicationAliases.put(expectedApplicationAlias);

		JSONObject expectedJson = new JSONObject();
		expectedJson.put("name", A_NAME);
		expectedJson.put("version", A_VERSION);
		expectedJson.put("productClass", A_CLASS_NAME);
		expectedJson.put("sourceRepository", A_SOURCE_REPOSITORY);
		expectedJson.put("deployToEnvironments", expectedEnvironmentDefinitions);
		expectedJson.put("aliasedAppDefs", expectedApplicationAliases);

		return expectedJson;
	}
}
