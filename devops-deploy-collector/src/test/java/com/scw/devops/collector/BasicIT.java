package com.scw.devops.collector;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;

import org.gitlab.api.models.GitlabProject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
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
import com.scw.devops.collector.vcs.gateway.GitlabGateway;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.common.data.ProjectVersion;

// NOTE: Must be in same package as Application class, other you get missing @SpringBootConfiguration errors
@RunWith(SpringRunner.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BasicIT {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	@Qualifier( "objectMapper" )
	private ObjectMapper jsonMapper;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private GitlabGateway gitlabGateway;

	private MockRestServiceServer mockServer;

	@BeforeEach
	public void init() {
		mockServer = MockRestServiceServer.createServer( restTemplate );
	}

	@Test
	public void runThruTest() throws Exception {
		// TODO - test all methods
		GitlabProject gitlabProject = new GitlabProject();
		Mockito.when( gitlabGateway.getProject( "products", "product1" ) ).thenReturn( gitlabProject );

		ProductDefinition def = new ProductDefinition( new DefinitionBase( "product1",
																					  new ProjectVersion( "1", false ),
																					  new HashMap<>(),
																					  "repo",
																		   Arrays.asList() ),
													   Arrays.asList() );
		mockServer
			.expect( ExpectedCount.once(),
					 MockRestRequestMatchers
						 .requestTo( new URI( "http://test:9090/store/update" ) ) )
			.andExpect( MockRestRequestMatchers.method( HttpMethod.POST ) )
			.andExpect( MockRestRequestMatchers.content()
				// TODO - better matching!
				.string( Matchers.stringContainsInOrder( "version",
														 "develop" ) ) )
			.andRespond( MockRestResponseCreators.withStatus( HttpStatus.OK ) );

		String response = get( "/ingest/product/product1", serialise( def ) );

		Assertions.assertTrue( response.isEmpty() );
		mockServer.verify();
	}

	private String serialise( final Object obj ) throws JsonGenerationException, JsonMappingException, IOException {
		StringWriter sw = new StringWriter( 2048 );
		jsonMapper.writeValue( sw, obj );
		return sw.toString();
	}

	private String get( final String uri, final String body ) throws Exception {
		var request = MockMvcRequestBuilders.get( uri ).contentType( MediaType.APPLICATION_JSON_UTF8 );
		return getResponseAsString(request);
	}

	private String getResponseAsString(final MockHttpServletRequestBuilder request) throws Exception {
		return this.mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse()
				.getContentAsString();
	}
}
