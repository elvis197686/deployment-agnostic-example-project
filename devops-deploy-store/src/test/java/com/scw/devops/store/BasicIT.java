package com.scw.devops.store;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntry;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.EnvironmentProductDefinitionReference;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.common.data.ProjectVersion;
import com.scw.devops.contract.store.query.command.GetAllApplicationDefinitionsCommand;
import com.scw.devops.contract.store.query.command.GetAllEnvironmentDefinitionsCommand;
import com.scw.devops.contract.store.query.command.GetAllProductDefinitionsCommand;
import com.scw.devops.contract.store.query.command.GetApplicationDefinitionCommand;
import com.scw.devops.contract.store.query.command.GetEnvironmentsWithProductDeployedCommand;
import com.scw.devops.contract.store.query.command.GetPreviousApplicationDefinitionCommand;
import com.scw.devops.contract.store.query.command.GetProductApplicationReferencesCommand;
import com.scw.devops.contract.store.query.command.GetProductDefinitionsForApplicationCommand;
import com.scw.devops.contract.store.query.command.GetProductDefinitionsForEnvironmentCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.data.VersionQuery;
import com.scw.devops.contract.store.update.command.AddApplicationDefinitionCommand;
import com.scw.devops.contract.store.update.command.AddEnvironmentDefinitionCommand;
import com.scw.devops.contract.store.update.command.AddProductDefinitionCommand;
import com.scw.devops.contract.store.update.command.StoreUpdateCommand;

// NOTE: Must be in same package as Application class, other you get missing @SpringBootConfiguration errors
@ExtendWith( SpringExtension.class )
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BasicIT {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	private ObjectMapper jsonMapper;

	@Test
	public void runThruTest() throws Exception {
		addApplicationDefinition( ( appDef ) -> new AddApplicationDefinitionCommand( appDef ) );
		addProductDefinition( ( productDef ) -> new AddProductDefinitionCommand( productDef ) );
		addEnvironmentDefinition( ( envDef ) -> new AddEnvironmentDefinitionCommand( envDef ) );

		// TODO Fix below tests to cater for above state
		runThruVersionQueryCall( ( versionQuery ) -> new GetAllEnvironmentDefinitionsCommand( versionQuery ) );
		runThruVersionQueryCall( ( versionQuery ) -> new GetAllProductDefinitionsCommand( versionQuery ) );
		runThruVersionQueryCall( ( versionQuery ) -> new GetAllApplicationDefinitionsCommand( versionQuery ) );
		runThruAppNameAndVersionCall( ( name, version ) -> new GetApplicationDefinitionCommand( name, version ) );
		runThruEnvProductDefQueryCall( ( productDef ) -> new GetEnvironmentsWithProductDeployedCommand( productDef ) );
		runThruApplicationDefCall( ( appDef ) -> new GetPreviousApplicationDefinitionCommand( appDef ) );
		runThruAppProductDefQueryCall( ( productDef ) -> new GetProductApplicationReferencesCommand( productDef ) );
		runThruProductNameAndVersionsCall( ( query ) -> new GetProductDefinitionsForApplicationCommand( query.name,
																										query.wantedVersionAsSingleString,
																										query.productVersionsToReturn ) );
		runThruProductNameAndVersionCall( ( name, version ) -> new GetProductDefinitionsForEnvironmentCommand( name, version ) );
	}

	private void addApplicationDefinition( final Function<ApplicationDefinition, StoreUpdateCommand> commandSupplier ) throws Exception {
		StoreUpdateCommand commandObj = commandSupplier.apply( new ApplicationDefinition( new DefinitionBase(
																											  "name",
																											 new ProjectVersion( "1", false ),
																											 new HashMap<>(),
																											 "repo",
																											 Arrays.asList() ) ) );
		String request = serialise( commandObj );
		String responseJson = post( "/update", request );

		// Success
		Assertions.assertTrue( responseJson.isEmpty() );
	}

	private void addProductDefinition( final Function<ProductDefinition, StoreUpdateCommand> commandSupplier ) throws Exception {
		ProductDefinition def = new ProductDefinition( new DefinitionBase( "name",
																		   new ProjectVersion( "1", false ),
																		   new HashMap<>(),
																		   "repo",
																		   Arrays.asList() ),
													   Arrays.asList( new ApplicationInstanceEntry( "appalias",
																									"alias",
																									new ProjectVersion( "2", false ) ) ) );
		StoreUpdateCommand commandObj = commandSupplier.apply( def );
		String request = serialise( commandObj );
		String responseJson = post( "/update", request );

		// Success
		Assertions.assertTrue( responseJson.isEmpty() );
	}

	private void addEnvironmentDefinition( final Function<EnvironmentDefinition, StoreUpdateCommand> commandSupplier ) throws Exception {
		StoreUpdateCommand commandObj = commandSupplier
			.apply( new EnvironmentDefinition( new DefinitionBase( "name",
																   new ProjectVersion( "1", false ),
																   new HashMap<>(),
																   "repo",
																   Arrays.asList() ),
											   Arrays
												   .asList( new EnvironmentProductDefinitionReference( "2", new ProjectVersion( "1", false ) ) ) ) );
		String request = serialise( commandObj );
		String responseJson = post( "/update", request );

		// Success
		Assertions.assertTrue( responseJson.isEmpty() );
	}

	private void runThruVersionQueryCall( final Function<VersionQuery, StoreQueryCommand> commandSupplier ) throws Exception {
		VersionQuery versionQuery = new VersionQuery();
		versionQuery.versionLimit = 2;
		StoreQueryCommand commandObj = commandSupplier.apply( versionQuery );
		String request = serialise( commandObj );
		String responseJson = post( "/query", request );

		String expectedJson = "{\"type\":\"" + commandObj.getClass()
			.getName() + "\",\"versionQuery\":{\"versionLimit\":2,\"wantedVersionIsWildcard\":false,\"wantedVersionIsPreview\":false,\"wantedVersionAsSemVer\":null,\"includePreview\":false},\"result\":[]}";

		// Note the order is not important
		JSONAssert.assertEquals( expectedJson, responseJson, JSONCompareMode.LENIENT );
	}

	private void runThruAppNameAndVersionCall( final BiFunction<String, String, StoreQueryCommand> commandSupplier ) throws Exception {
		String name = "aname";
		String version = "1";
		StoreQueryCommand commandObj = commandSupplier.apply( name, version );
		String request = serialise( commandObj );
		String responseJson = post( "/query", request );

		String expectedJson = "{\"type\":\"" + commandObj.getClass()
			.getName() + "\",\"versionQuery\":{\"versionLimit\":2,\"wantedVersionIsWildcard\":false,\"wantedVersionIsPreview\":false,\"wantedVersionAsSemVer\":null,\"includePreview\":false},\"result\":[]}";

		// Note the order is not important
		JSONAssert.assertEquals( expectedJson, responseJson, JSONCompareMode.LENIENT );
	}

	private void runThruEnvProductDefQueryCall( final Function<ProductDefinition, StoreQueryCommand> commandSupplier ) throws Exception {
		ProductDefinition def = new ProductDefinition( new DefinitionBase( "name",
																		   new ProjectVersion( "1", false ),
																		   new HashMap<>(),
																		   "repo",
																		   Arrays.asList() ),
													   Arrays.asList( new ApplicationInstanceEntry( "appalias",
																									"alias",
																									new ProjectVersion( "2", false ) ) ) );
		StoreQueryCommand commandObj = commandSupplier.apply( def );
		String request = serialise( commandObj );
		String responseJson = post( "/query", request );

		String expectedJson = "{\"type\":\"" + commandObj.getClass()
			.getName() + "\",\"versionQuery\":{\"versionLimit\":2,\"wantedVersionIsWildcard\":false,\"wantedVersionIsPreview\":false,\"wantedVersionAsSemVer\":null,\"includePreview\":false},\"result\":[]}";

		// Note the order is not important
		JSONAssert.assertEquals( expectedJson, responseJson, JSONCompareMode.LENIENT );
	}

	private void runThruApplicationDefCall( final Function<ApplicationDefinition, StoreQueryCommand> commandSupplier ) throws Exception {
		ApplicationDefinition def = new ApplicationDefinition( new DefinitionBase( "name",
																				   new ProjectVersion( "1", false ),
																				   new HashMap<>(),
																				   "repo",
																				   Arrays.asList() ) );
		StoreQueryCommand commandObj = commandSupplier.apply( def );
		String request = serialise( commandObj );
		String responseJson = post( "/query", request );

		String expectedJson = "{\"type\":\"" + commandObj.getClass()
			.getName() + "\",\"versionQuery\":{\"versionLimit\":2,\"wantedVersionIsWildcard\":false,\"wantedVersionIsPreview\":false,\"wantedVersionAsSemVer\":null,\"includePreview\":false},\"result\":[]}";

		// Note the order is not important
		JSONAssert.assertEquals( expectedJson, responseJson, JSONCompareMode.LENIENT );
	}

	private void runThruAppProductDefQueryCall( final Function<ProductDefinition, StoreQueryCommand> commandSupplier ) throws Exception {
		ProductDefinition def = new ProductDefinition( new DefinitionBase( "name",
																		   new ProjectVersion( "1", false ),
																		   new HashMap<>(),
																		   "repo",
																		   Arrays.asList() ),
													   Arrays.asList( new ApplicationInstanceEntry( "appalias",
																									"alias",
																									new ProjectVersion( "2", false ) ) ) );
		StoreQueryCommand commandObj = commandSupplier.apply( def );
		String request = serialise( commandObj );
		String responseJson = post( "/query", request );

		String expectedJson = "{\"type\":\"" + commandObj.getClass()
			.getName() + "\",\"versionQuery\":{\"versionLimit\":2,\"wantedVersionIsWildcard\":false,\"wantedVersionIsPreview\":false,\"wantedVersionAsSemVer\":null,\"includePreview\":false},\"result\":[]}";

		// Note the order is not important
		JSONAssert.assertEquals( expectedJson, responseJson, JSONCompareMode.LENIENT );
	}

	private void runThruProductNameAndVersionsCall( final Function<GetProductDefinitionsForApplicationQuery, StoreQueryCommand> commandSupplier )
		throws Exception {
		GetProductDefinitionsForApplicationQuery def = new GetProductDefinitionsForApplicationQuery();
		def.name = "product1";
		def.wantedVersionAsSingleString = "1";
		def.productVersionsToReturn = new VersionQuery();
		def.productVersionsToReturn.versionLimit = 2;

		StoreQueryCommand commandObj = commandSupplier.apply( def );
		String request = serialise( commandObj );
		String responseJson = post( "/query", request );

		String expectedJson = "{\"type\":\"" + commandObj.getClass()
			.getName() + "\",\"versionQuery\":{\"versionLimit\":2,\"wantedVersionIsWildcard\":false,\"wantedVersionIsPreview\":false,\"wantedVersionAsSemVer\":null,\"includePreview\":false},\"result\":[]}";

		// Note the order is not important
		JSONAssert.assertEquals( expectedJson, responseJson, JSONCompareMode.LENIENT );
	}

	private void runThruProductNameAndVersionCall( final BiFunction<String, String, StoreQueryCommand> commandSupplier ) throws Exception {
		String name = "aname";
		String version = "1";
		StoreQueryCommand commandObj = commandSupplier.apply( name, version );
		String request = serialise( commandObj );
		String responseJson = post( "/query", request );

		String expectedJson = "{\"type\":\"" + commandObj.getClass()
			.getName() + "\",\"versionQuery\":{\"versionLimit\":2,\"wantedVersionIsWildcard\":false,\"wantedVersionIsPreview\":false,\"wantedVersionAsSemVer\":null,\"includePreview\":false},\"result\":[]}";

		// Note the order is not important
		JSONAssert.assertEquals( expectedJson, responseJson, JSONCompareMode.LENIENT );
	}

	private String serialise( final Object obj ) throws JsonGenerationException, JsonMappingException, IOException {
		StringWriter sw = new StringWriter( 2048 );
		jsonMapper.writeValue( sw, obj );
		return sw.toString();
	}

	private String post(final String uri, final String body) throws Exception {
		var request = MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_UTF8).content(body);
		return getResponseAsString(request);
	}

	private String getResponseAsString(final MockHttpServletRequestBuilder request) throws Exception {
		return this.mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse()
				.getContentAsString();
	}

	private static class GetProductDefinitionsForApplicationQuery {

		String name;
		String wantedVersionAsSingleString;
		VersionQuery productVersionsToReturn;
	}
}
