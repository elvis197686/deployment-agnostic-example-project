package com.scw.devops.contract.store.common.data;

import java.util.List;

import com.scw.devops.contract.store.query.data.ApplicationInstance;
import com.scw.devops.contract.store.query.data.ClientApplicationInstanceTransformer;
import com.scw.devops.query.controller.response.ProductDef;

public class ClientProductDefinitionTransformer {

	public static ProductDef transformTo( final ProductDefinition def,
										  final List<EnvironmentDefinition> deployedToEnvironments,
										  final List<ApplicationInstance> applicationInstances ) {
		return new ProductDef( def.base.name,
							   SharedProjectVersionProcessor
								   .getSingleVersionString( def.base.version ),
							   ClientProductDefinitionProcessor.getClassName( def )
								   .orElse( null ),
							   def.base.sourceRepository,
							   ClientEnvironmentDefinitionTransformer
								   .transformTo( deployedToEnvironments ),
							   ClientApplicationInstanceTransformer.transformToAliasedAppDefs( applicationInstances ),
							   ClientConfigurationErrorDataTransformer.transformToSingleError( def.base.errors ) );
	}

}
