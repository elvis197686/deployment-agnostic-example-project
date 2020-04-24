package com.scw.devops.contract.store.query.data;

import java.util.List;
import java.util.stream.Collectors;

import com.scw.devops.contract.store.common.data.ClientApplicationDefinitionTransformer;
import com.scw.devops.query.controller.response.ApplicationAlias;

public class ClientApplicationInstanceTransformer {

	public static List<ApplicationAlias> transformToAliasedAppDefs( final List<ApplicationInstance> instances ) {
		if ( instances == null ) {
			return null;
		}
		return instances.stream().map( ClientApplicationInstanceTransformer::transformToAliasedAppDef ).collect( Collectors.toList() );
	}

	private static ApplicationAlias transformToAliasedAppDef( final ApplicationInstance instance ) {
		return new ApplicationAlias( instance.alias, ClientApplicationDefinitionTransformer.transformTo( instance.applicationDefinition ) );
	}

}
