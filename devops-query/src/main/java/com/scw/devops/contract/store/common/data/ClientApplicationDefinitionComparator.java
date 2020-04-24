package com.scw.devops.contract.store.common.data;

public class ClientApplicationDefinitionComparator {

	public static int compareWithNameAscThenVersionDesc( final ApplicationDefinition a, final ApplicationDefinition b ) {
		return ClientDefinitionBaseComparator.compareWithNameAscThenVersionDesc( a.base, b.base );
	}

}
