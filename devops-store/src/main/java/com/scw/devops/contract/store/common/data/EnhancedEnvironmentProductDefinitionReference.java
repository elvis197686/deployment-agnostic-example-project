package com.scw.devops.contract.store.common.data;

import java.util.function.Function;

import com.scw.devops.domain.projectversion.ProjectVersion;
import com.scw.devops.store.service.ProductDefinitionReferenceQuery;
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

	public ProductDefinitionReferenceQuery getProductDefinitionReference( final Function<String, ProjectVersion> versionResolver ) {
		ProjectVersion versionToUse = version.getOrResolveWildcard( originalDefinition.name, versionResolver );
		return new ProductDefinitionReferenceQuery( originalDefinition.name, versionToUse );
	}
}
