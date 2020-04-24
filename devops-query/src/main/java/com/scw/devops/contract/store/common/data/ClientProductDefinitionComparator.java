package com.scw.devops.contract.store.common.data;

public class ClientProductDefinitionComparator {

	public static int compareWithNameAscThenVersionDesc( final ProductDefinition a, final ProductDefinition b ) {
		return ClientDefinitionBaseComparator.compareWithNameAscThenVersionDesc( a.base, b.base );
	}

}
