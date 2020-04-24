package com.scw.devops.contract.store.common.data;

public class ClientEnvironmentDefinitionProcessor {

	static final String AUTO_START_KEY = "autoStart";

	public static Boolean getAutoStart( final EnvironmentDefinition def ) {
		return (Boolean) ( def.base.arbitraryProperties == null ? null : def.base.arbitraryProperties.getOrDefault( AUTO_START_KEY, null ) );
	}

}
