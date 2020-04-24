package com.scw.devops.contract.store.common.data;

public class ApplicationDefinitionProcessor {

	public static String getName( final ApplicationDefinition definition ) {
		return definition.base.name;
	}

	public static MappableSortableProjectVersion getVersion( final ApplicationDefinition definition ) {
		return new MappableSortableProjectVersion(definition.base.version, definition.base.version.version);
	}

	public static MappableDefinitionReference getDefinitionReference( final ApplicationDefinition definition ) {
		return new MappableDefinitionReference( definition.base.name, definition.base.version );
	}

}
