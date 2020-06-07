package com.scw.devops.query;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.contract.query.data.StandardQueryInputFilter;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.query.command.OutputApplicationDefinitions;
import com.scw.devops.contract.store.query.data.VersionQuery;
import com.scw.devops.domain.projectversion.ProjectVersion;
import com.scw.devops.query.controller.request.StandardQueryBody;

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
	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;

	@BeforeEach
	public void init() {
		mockServer = MockRestServiceServer.createServer( restTemplate );
	}

	@Test
	public void runThruTest() throws Exception {
		// TODO - test all methods
		VersionQuery versionQuery = new VersionQuery();
		versionQuery.versionLimit = 2;
		OutputApplicationDefinitions result = new OutputApplicationDefinitions( Arrays
			.asList( new ApplicationDefinition( new DefinitionBase( "name", ProjectVersion.namedVersion( "1" ), null, "sourceRepo", null ) ) ) );

		String responseString = serialise( result );
		mockServer.expect( ExpectedCount.once(), MockRestRequestMatchers.requestTo( new URI( "http://test:9090/store/query" ) ) )
			.andExpect( MockRestRequestMatchers.method( HttpMethod.POST ) )
			.andRespond( MockRestResponseCreators.withStatus( HttpStatus.OK )
				.contentType( MediaType.APPLICATION_JSON )
				.body( responseString ) );

		String responseJson = post( "/applications",
									serialise( new StandardQueryBody( new StandardQueryInputFilter( Optional
										.of( "a*" ), Optional.empty(), Optional.of( true ), Optional.empty(), Optional.empty() ) ) ) );

		String expectedJson = "{\"apps\":[{\"name\":\"name\",\"version\":\"1\",\"sourceRepository\":\"sourceRepo\",\"imageRepository\":null,\"applicationRuntime\":\"UNKNOWN\",\"error\":null}]}";

		// Note the order is not important
		mockServer.verify();
		JSONAssert.assertEquals( expectedJson, responseJson, JSONCompareMode.LENIENT );
	}

	private String serialise( final Object obj ) throws JsonGenerationException, JsonMappingException, IOException {
		StringWriter sw = new StringWriter( 2048 );
		jsonMapper.writeValue( sw, obj );
		return sw.toString();
	}

	private String post(final String uri, final String body) throws Exception {
		var request = MockMvcRequestBuilders.post( uri ).contentType( MediaType.APPLICATION_JSON_UTF8 ).param( "versionLimit", "2" ).content( body );
		return getResponseAsString(request);
	}

	private String getResponseAsString(final MockHttpServletRequestBuilder request) throws Exception {
		return this.mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse()
				.getContentAsString();
	}
}
