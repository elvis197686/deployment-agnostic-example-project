package com.scw.devops.store.state;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.EnhancedEnvironmentDefinition;
import com.scw.devops.contract.store.common.data.MappableDefinitionReference;
import com.scw.devops.contract.store.common.data.MappableSortableProjectVersion;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.common.data.ProjectVersion;

public class DataStore {

	private final Map<String, Map<MappableSortableProjectVersion, EnhancedEnvironmentDefinition>> environmentsByVersionByName = new HashMap<>();
	private final Map<String, Map<MappableSortableProjectVersion, ProductDefinition>> productsByVersionByName = new HashMap<>();
	private final Map<String, Map<MappableSortableProjectVersion, ApplicationDefinition>> applicationsByVersionByName = new HashMap<>();
	// Maps must be private as they are mutable
	private Map<MappableDefinitionReference, List<EnhancedEnvironmentDefinition>> environmentsByProduct = new HashMap<>();
	private Map<MappableDefinitionReference, List<ProductDefinition>> productsByApplication = new HashMap<>();

	public void addEnvironmentDefinition( final String definitionName, final MappableSortableProjectVersion version,
										  final EnhancedEnvironmentDefinition enhancedEnvironmentDefinition )
		throws Exception {
		environmentsByVersionByName.compute( definitionName, ( k, v ) -> {
			if ( v == null ) {
				v = new HashMap<>();
			}
			v.put( version, enhancedEnvironmentDefinition );
			return v;
		} );
	}

	public void addProductDefinition( final String definitionName, final MappableSortableProjectVersion version, final ProductDefinition definition )
		throws Exception {
		productsByVersionByName.compute( definitionName, ( k, v ) -> {
			if ( v == null ) {
				v = new HashMap<>();
			}
			v.put( version, definition );
			return v;
		} );
	}

	public void addApplicationDefinition( final String definitionName, final MappableSortableProjectVersion version,
										  final ApplicationDefinition definition ) {
		applicationsByVersionByName.compute( definitionName, ( k, v ) -> {
			if ( v == null ) {
				v = new HashMap<>();
			}
			v.put( version, definition );
			return v;
		} );
	}

	public List<EnhancedEnvironmentDefinition> getCurrentEnvironmentList() {
		return environmentsByVersionByName.entrySet().stream().flatMap( e -> e.getValue().values().stream() ).collect( Collectors.toList() );
	}

	public List<ProductDefinition> getCurrentProductList() {
		return productsByVersionByName.entrySet().stream().flatMap( e -> e.getValue().values().stream() ).collect( Collectors.toList() );
	}

	public void replaceEnvironmentsByProductMap(
												 final Map<MappableDefinitionReference, List<EnhancedEnvironmentDefinition>> environmentsByProductMap ) {
		environmentsByProduct = Collections.unmodifiableMap(environmentsByProductMap);
	}

	public List<EnhancedEnvironmentDefinition> getEnvironmentsForProduct( final MappableDefinitionReference product ) {
		return environmentsByProduct.getOrDefault(product, Arrays.asList());
	}

	public void replaceProductsByApplicationMap(
										  final Map<MappableDefinitionReference, List<ProductDefinition>> productsByApplicationMap ) {
		productsByApplication = Collections.unmodifiableMap(productsByApplicationMap);
	}

	public List<ProductDefinition> getProductsForApplication(
															  final MappableDefinitionReference applicationDefinitionReference ) {
		return productsByApplication.getOrDefault(applicationDefinitionReference, Arrays.asList());
	}

	public ProjectVersion getProductLastTag( final String productName ) {
		Map<MappableSortableProjectVersion, ProductDefinition> productVersions = productsByVersionByName.get( productName );
		if (productVersions != null) {
			Optional<MappableSortableProjectVersion> lastTag = productVersions.keySet()
				.stream()
				.sorted( ( a, b ) -> a.compareTo( b ) )
					.filter((v) -> !v.isPreview()).findFirst();
			if (lastTag.isPresent()) {
				return lastTag.get().getProjectVersion();
			}
		}
		// Use latest if nothing found
		return new ProjectVersion("preview", true);
	}

	public Map<String, Map<MappableSortableProjectVersion, EnhancedEnvironmentDefinition>> getEnvironmentsByVersionByName() {
		return environmentsByVersionByName;
	}

	public Map<String, Map<MappableSortableProjectVersion, ProductDefinition>> getProductsByVersionByName() {
		return productsByVersionByName;
	}

	public Map<String, Map<MappableSortableProjectVersion, ApplicationDefinition>> getApplicationsByVersionByName() {
		return applicationsByVersionByName;
	}
}
