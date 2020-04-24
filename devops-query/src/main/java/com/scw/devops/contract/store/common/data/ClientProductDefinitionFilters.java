package com.scw.devops.contract.store.common.data;

import java.util.Optional;

public class ClientProductDefinitionFilters {

	static final String CLASS_KEY = "productClass";

	public static boolean filterByName( final ProductDefinition def, final Optional<String> wantedName ) {
		return ClientFilters.filterByName( def.base.name, wantedName );
	}

	public static boolean filterByErrorState( final ProductDefinition def, final Optional<Boolean> wantedValidity ) {
		return ClientFilters.filterByErrorState( hasErrors( def ), wantedValidity );
	}

	private static boolean hasErrors( final ProductDefinition def ) {
		return def.base.errors != null && def.base.errors.size() > 0;
	}

	public static boolean filterByExactString( final ProductDefinition def, final boolean unsetClassOnly, final Optional<String> wantedClass ) {
		return ClientFilters.filterByExactString( getClassName( def ), unsetClassOnly, wantedClass );
	}

	private static Optional<String> getClassName( final ProductDefinition def ) {
		return Optional
			.ofNullable( (String) ( def.base.arbitraryProperties == null ? null : def.base.arbitraryProperties.getOrDefault( CLASS_KEY, null ) ) );
	}

}
