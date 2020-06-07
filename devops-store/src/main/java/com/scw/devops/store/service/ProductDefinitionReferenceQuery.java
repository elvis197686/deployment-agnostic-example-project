package com.scw.devops.store.service;

import java.util.Map;
import java.util.Optional;

import com.scw.devops.contract.store.common.data.MappableSortableProjectVersion;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.domain.projectversion.ProjectVersion;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProductDefinitionReferenceQuery {

	private final String name;
	private final ProjectVersion projectVersion;

	public Optional<ProductDefinition> getProduct( final Map<String, Map<MappableSortableProjectVersion, ProductDefinition>> productMap ) {
		Map<MappableSortableProjectVersion, ProductDefinition> productDefinitionsByVersion = productMap.get( name );
		if ( productDefinitionsByVersion != null ) {
			return Optional
				.ofNullable( productDefinitionsByVersion
					.getOrDefault( new MappableSortableProjectVersion( projectVersion ), null ) );
		}
		return Optional.empty();
	}
}
