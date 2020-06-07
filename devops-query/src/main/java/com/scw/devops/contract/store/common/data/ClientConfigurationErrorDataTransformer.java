package com.scw.devops.contract.store.common.data;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.scw.devops.contract.query.data.ConfigurationErrorData;

public class ClientConfigurationErrorDataTransformer {

	public static Optional<ConfigurationErrorData> transformToSingleError( final Collection<ConfigurationError> errors ) {
		return Optional
			.ofNullable( ( errors == null || errors.size() < 1 ) ? null
																 : ClientConfigurationErrorDataTransformer.transformTo( errors.iterator().next() ) );
	}

	public static Collection<ConfigurationErrorData> transformTo( final Collection<ConfigurationError> errors ) {
		if ( errors == null ) {
			return null;
		}
		return errors.stream().map( ClientConfigurationErrorDataTransformer::transformTo ).collect( Collectors.toList() );
	}

	public static ConfigurationErrorData transformTo( final ConfigurationError error ) {
		return new ConfigurationErrorData( error.message );
	}

}
