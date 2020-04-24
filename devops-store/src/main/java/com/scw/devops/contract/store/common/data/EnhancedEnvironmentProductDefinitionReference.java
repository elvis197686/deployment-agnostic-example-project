package com.scw.devops.contract.store.common.data;

import java.util.function.Function;

import com.scw.devops.store.service.ProductDefinitionReference;
import com.scw.devops.store.service.ProjectVersionWithWildcard;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class EnhancedEnvironmentProductDefinitionReference {

	final EnvironmentProductDefinitionReference originalDefinition;
	final ProjectVersionWithWildcard version;

	public MappableDefinitionReference getOrResolveWildcard( final Function<String, ProjectVersion> productVersionResolver ) {
		return new MappableDefinitionReference( originalDefinition.name,
												version.getOrResolveWildcard( originalDefinition.name, productVersionResolver ) );
	}

	public EnvironmentProductDefinitionReference getOriginalDefinition() {
		return originalDefinition;
	}

	public ProductDefinitionReference getProductDefinitionReference( final Function<String, ProjectVersion> versionResolver ) {
		ProjectVersion versionToUse = version.getOrResolveWildcard( originalDefinition.name, versionResolver );
		return new ProductDefinitionReference( originalDefinition.name, SharedProjectVersionProcessor.getSingleVersionString( versionToUse ) );
	}
}
