package com.scw.devops.contract.serialisation;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.contract.store.query.command.GetAllApplicationDefinitionsCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.data.TestVersionQueryProcessor;
import com.scw.devops.contract.store.query.data.VersionQuery;
import com.scw.devops.deploy.config.ApplicationConfiguration;

// TODO - remove - only used to set up tests!
public class QuerySerailiser {

	private static String SERIALISED_VERSION = "{\"type\":\"com.scw.devops.contract.store.query.command.GetAllApplicationDefinitionsCommand\",\"versionQuery\":{\"versionLimit\":2,\"wantedVersionIsWildcard\":false,\"wantedVersionIsPreview\":false,\"wantedVersionAsSemVer\":null,\"includePreview\":false},\"result\":null}";

	private static String SERIALISED_QUERY = "{\"versionLimit\":2,\"wantedVersionIsWildcard\":false,\"wantedVersionIsPreview\":false,\"wantedVersionAsSemVer\":null,\"includePreview\":false}";

	private ObjectMapper objectMapper = new ApplicationConfiguration().objectMapper();

	@Test
	public void deserialiseBaseCommand() throws Exception {
		VersionQuery versionQuery = new VersionQuery();
		versionQuery.versionLimit = 2;
		String jsonString = JsonMapper.serialize( new GetAllApplicationDefinitionsCommand( versionQuery ) );

		final StoreQueryCommand command = objectMapper.readValue( jsonString, StoreQueryCommand.class );

		Assertions.assertTrue( command.getClass().getSimpleName().equals( "GetAllApplicationDefinitionsCommand" ) );
	}

	@Test
	public void deserialiseQuery() throws Exception {
		String jsonString = SERIALISED_QUERY;

		final VersionQuery command = objectMapper.readValue( jsonString, VersionQuery.class );

		Assertions.assertTrue( TestVersionQueryProcessor.getLimit( command ) == 2 );
	}

	@Test
	public void serialiseQuery() throws Exception {
		VersionQuery versionQuery = new VersionQuery();
		versionQuery.versionLimit = 2;

		String request = objectMapper.writeValueAsString( new GetAllApplicationDefinitionsCommand( versionQuery ) );

		Assertions.assertTrue( request
			.equals( SERIALISED_VERSION ) );
	}

	private static class JsonMapper {

		private static final int INITIAL_SIZE = 2048;

		private static ObjectMapper mapper;

	   static
	   {
	      mapper = new ObjectMapper();

		  mapper.setVisibility( mapper.getSerializationConfig()
			  .getDefaultVisibilityChecker()
			  .withFieldVisibility( JsonAutoDetect.Visibility.ANY )
			  .withGetterVisibility( JsonAutoDetect.Visibility.NONE )
			  .withSetterVisibility( JsonAutoDetect.Visibility.NONE )
			  .withCreatorVisibility( JsonAutoDetect.Visibility.NONE ) );
		  mapper.addMixIn( GetAllApplicationDefinitionsCommand.class, GetAllApplicationDefinitionsCommandMixin.class );
		  mapper.addMixIn( StoreQueryCommand.class, StoreQueryCommandMixin.class );
	   }

		public static <T> String serialize( final T o ) throws IOException {
			StringWriter sw = new StringWriter( INITIAL_SIZE );
			mapper.writeValue( sw, o );

			return sw.toString();
		}

		public static <T> T deserialize( final String source, final Class<T> targetClass ) throws IOException {
			return mapper.readValue( source, targetClass );
		}
	}

}
