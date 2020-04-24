package com.scw.devops.contract.store.common.data;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class ClientDefinitionBaseComparator {

	public static int compareWithNameAscThenVersionDesc( final DefinitionBase a, final DefinitionBase b ) {
		return new CompareToBuilder().append( a.name, b.name ).appendSuper( ClientProjectVersionComparator.compareTo( a.version, b.version ) ).build();
	}

}
