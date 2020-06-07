package com.scw.devops.contract.store.common.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.scw.devops.store.service.ProjectVersionWithWildcard;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class EnhancedEnvironmentDefinition {

	final EnvironmentDefinition originalDefinition;
	final Collection<EnhancedEnvironmentProductDefinitionReference> productsInEnvironmentWithWildcard;

	public Collection<EnhancedEnvironmentProductDefinitionReference> getProductsInEnvironment() {
		return ( originalDefinition == null ) ? Arrays.asList() : productsInEnvironmentWithWildcard;
	}

	public String getName() {
		return originalDefinition.base.name;
	}

	public MappableSortableProjectVersion getVersion() {
		return new MappableSortableProjectVersion( originalDefinition.base.version );
	}

	public static EnhancedEnvironmentDefinition transform( final EnvironmentDefinition def ) {
		return new EnhancedEnvironmentDefinition( def, def.productsInEnvironment.stream().map( p -> transform( p ) ).collect( Collectors.toList() ) );
	}

	private static EnhancedEnvironmentProductDefinitionReference transform( final EnvironmentProductDefinitionReference defRef ) {
		return new EnhancedEnvironmentProductDefinitionReference( defRef, ProjectVersionWithWildcard.fromVersion( defRef.version ) );
	}

	public EnvironmentDefinition getOriginalDefinition() {
		return originalDefinition;
	}

	public boolean isPreviewVersion() {
		return originalDefinition.base.version.isPreview();
	}
}
