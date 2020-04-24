package com.scw.devops.contract.store.common.data;

import java.util.Arrays;
import java.util.Collection;

public class EnvironmentDefinitionProcessor {

	public static Collection<EnhancedEnvironmentProductDefinitionReference> getProductsInEnvironment( final EnhancedEnvironmentDefinition def ) {
		return ( def.originalDefinition == null ) ? Arrays.asList() : def.productsInEnvironmentWithWildcard;
	}

	public static String getName( final EnhancedEnvironmentDefinition definition ) {
		return definition.originalDefinition.base.name;
	}

	public static MappableSortableProjectVersion getVersion( final EnhancedEnvironmentDefinition definition ) {
		return new MappableSortableProjectVersion(definition.originalDefinition.base.version, definition.originalDefinition.base.version.version);
	}

}
