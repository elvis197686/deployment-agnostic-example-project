package com.scw.devops.test.integration;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.contract.query.command.GetProductsCommand;
import com.scw.devops.contract.query.data.StandardQueryInputFilter;
import com.scw.devops.deploy.config.ApplicationConfiguration;

// TODO - remove - only used to set up tests!
public class QuerySerailiser {

	private ObjectMapper objectMapper = new ApplicationConfiguration().objectMapper();

	@Test
	public void deserialiseQueryCommand() throws Exception {

		String jsonString = objectMapper.writeValueAsString( new GetProductsCommand( Optional.of( 2 ),
																					 new StandardQueryInputFilter( Optional.of( "name" ),
																												   Optional.of( "1" ),
																												   Optional.of( false ),
																												   Optional.of( "valid" ),
																												   Optional.of( "product" ) ) ) );

		System.out.println( "RESULT: " + jsonString );
	}

}
