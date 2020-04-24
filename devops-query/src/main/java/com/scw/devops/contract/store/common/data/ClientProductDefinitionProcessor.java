package com.scw.devops.contract.store.common.data;

import java.util.Optional;

public class ClientProductDefinitionProcessor {

	private static final String CLASS_KEY = "productClass";

	public static Optional<String> getClassName( final ProductDefinition def ) {
		return Optional
			.ofNullable( (String) ( def.base.arbitraryProperties == null ? null : def.base.arbitraryProperties.getOrDefault( CLASS_KEY, null ) ) );
	}

}
