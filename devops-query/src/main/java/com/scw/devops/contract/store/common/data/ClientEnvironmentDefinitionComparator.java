package com.scw.devops.contract.store.common.data;

public class ClientEnvironmentDefinitionComparator {

	public static int compareWithNameAscThenVersionDesc( final EnvironmentDefinition a, final EnvironmentDefinition b ) {
		return ClientDefinitionBaseComparator.compareWithNameAscThenVersionDesc( a.base, b.base );
	}

}
