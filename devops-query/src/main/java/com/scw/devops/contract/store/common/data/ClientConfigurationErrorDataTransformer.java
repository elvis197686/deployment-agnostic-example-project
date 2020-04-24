package com.scw.devops.contract.store.common.data;

import java.util.Collection;
import java.util.stream.Collectors;

import com.scw.devops.query.controller.response.ConfigurationErrorData;

public class ClientConfigurationErrorDataTransformer {

	public static ConfigurationErrorData transformToSingleError( final Collection<ConfigurationError> errors ) {
		return ( errors == null || errors.size() < 1 ) ? null : ClientConfigurationErrorDataTransformer.transformTo( errors.iterator().next() );
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
