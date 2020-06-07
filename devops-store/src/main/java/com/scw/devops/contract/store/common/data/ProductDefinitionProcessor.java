package com.scw.devops.contract.store.common.data;

import java.util.Arrays;
import java.util.Collection;

public class ProductDefinitionProcessor {

	public static Collection<ApplicationInstanceEntry> getApplicationsInProduct( final ProductDefinition definition ) {
		return ( definition.applicationsInProduct == null ) ? Arrays.asList() : definition.applicationsInProduct;
	}

	public static String getName( final ProductDefinition definition ) {
		return definition.base.name;
	}

	public static MappableSortableProjectVersion getVersion( final ProductDefinition definition ) {
		return new MappableSortableProjectVersion( definition.base.version );
	}

	public static MappableDefinitionReference getDefinitionReference( final ProductDefinition definition ) {
		return new MappableDefinitionReference( definition.base.name, definition.base.version );
	}
}
