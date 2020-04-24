package com.scw.devops.contract.store.common.data;

import java.util.Optional;

public class ClientApplicationDefinitionFilters {

	public static boolean filterByName( final ApplicationDefinition def, final Optional<String> wantedName ) {
		return ClientFilters.filterByName( def.base.name, wantedName );
	}

	public static boolean filterByErrorState( final ApplicationDefinition def, final Optional<Boolean> wantedValidity ) {
		return ClientFilters.filterByErrorState( hasErrors( def ), wantedValidity );
	}

	private static boolean hasErrors( final ApplicationDefinition def ) {
		return def.base.errors != null && def.base.errors.size() > 0;
	}

}
