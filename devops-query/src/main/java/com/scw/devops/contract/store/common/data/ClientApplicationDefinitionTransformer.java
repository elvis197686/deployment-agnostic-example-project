package com.scw.devops.contract.store.common.data;

import com.scw.devops.query.controller.response.ApplicationDef;

public class ClientApplicationDefinitionTransformer {

	private static final String IMAGE_REPOSITORY_KEY = "imageRepository";
	private static final String RUNTIME_KEY = "runtime";

	public static ApplicationDef transformTo( final ApplicationDefinition def ) {
		return new ApplicationDef( def.base.name,
								   SharedProjectVersionProcessor
									   .getSingleVersionString( def.base.version ),
								   def.base.sourceRepository,
								   getImageRepository( def ),
								   getGraphQLFriendlyRuntime( def ),
								   ClientConfigurationErrorDataTransformer.transformToSingleError( def.base.errors ) );
	}

	private static String getImageRepository( final ApplicationDefinition def ) {
		return (String) ( def.base.arbitraryProperties == null ? null : def.base.arbitraryProperties.getOrDefault( IMAGE_REPOSITORY_KEY, null ) );
	}

	private static String getGraphQLFriendlyRuntime( final ApplicationDefinition def ) {
		String runtime = (String) ( def.base.arbitraryProperties == null ? null : def.base.arbitraryProperties.getOrDefault( RUNTIME_KEY, null ) );
		return ( runtime == null ) ? "UNKNOWN" : runtime.replace( '-', '_' ).toUpperCase();
	}
}
