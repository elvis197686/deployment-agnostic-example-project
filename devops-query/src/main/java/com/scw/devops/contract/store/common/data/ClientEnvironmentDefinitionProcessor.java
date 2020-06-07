package com.scw.devops.contract.store.common.data;

import java.util.Optional;

public class ClientEnvironmentDefinitionProcessor {

	static final String AUTO_START_KEY = "autoStart";

	public static Optional<Boolean> getAutoStart( final EnvironmentDefinition def ) {
		return Optional
			.ofNullable( (Boolean) ( def.base.arbitraryProperties == null ? null
																		  : def.base.arbitraryProperties.getOrDefault( AUTO_START_KEY, null ) ) );
	}

}
