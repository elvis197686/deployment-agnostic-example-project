package com.scw.devops.contract.store.query.data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.scw.devops.contract.query.data.ApplicationAlias;
import com.scw.devops.contract.store.common.data.ClientApplicationDefinitionTransformer;

public class ClientApplicationInstanceTransformer {

	public static Optional<List<ApplicationAlias>> transformToAliasedAppDefs( final List<ApplicationInstance> instances ) {
		if ( instances == null ) {
			return Optional.empty();
		}
		return Optional.of( instances.stream().map( ClientApplicationInstanceTransformer::transformToAliasedAppDef ).collect( Collectors.toList() ) );
	}

	private static ApplicationAlias transformToAliasedAppDef( final ApplicationInstance instance ) {
		return new ApplicationAlias( instance.alias, ClientApplicationDefinitionTransformer.transformTo( instance.applicationDefinition ) );
	}

}