package com.scw.devops.contract.store.common.data;

import java.util.List;

import com.scw.devops.contract.query.data.ProductDef;
import com.scw.devops.contract.store.query.data.ApplicationInstance;
import com.scw.devops.contract.store.query.data.ClientApplicationInstanceTransformer;

public class ClientProductDefinitionTransformer {

	public static ProductDef transformTo( final ProductDefinition def,
										  final List<EnvironmentDefinition> deployedToEnvironments,
										  final List<ApplicationInstance> applicationInstances ) {
		return new ProductDef( def.base.name,
							   ClientProjectVersionFormatter
								   .getSingleVersionString( def.base.version ),
							   ClientProductDefinitionProcessor
								   .getClassName( def ),
							   def.base.sourceRepository,
							   ClientEnvironmentDefinitionTransformer
								   .transformTo( deployedToEnvironments ),
							   ClientApplicationInstanceTransformer.transformToAliasedAppDefs( applicationInstances ),
							   ClientConfigurationErrorDataTransformer.transformToSingleError( def.base.errors ) );
	}

}
