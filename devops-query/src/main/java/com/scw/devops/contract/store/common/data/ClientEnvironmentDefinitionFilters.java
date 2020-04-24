package com.scw.devops.contract.store.common.data;

import java.util.Optional;

public class ClientEnvironmentDefinitionFilters {

	public static boolean filterByName( final EnvironmentDefinition def, final Optional<String> wantedName ) {
		return ClientFilters.filterByName( def.base.name, wantedName );
	}

	public static boolean filterByBooleanProperty( final EnvironmentDefinition def,
												   final Optional<Boolean> autoStartWanted ) {
		return ClientFilters.filterByBooleanProperty( ClientEnvironmentDefinitionProcessor.getAutoStart( def ), false, autoStartWanted );
	}

}
