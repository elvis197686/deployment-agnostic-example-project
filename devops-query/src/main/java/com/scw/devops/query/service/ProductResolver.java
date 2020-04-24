package com.scw.devops.query.service;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.ClientEnvironmentDefinitionComparator;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.common.data.ClientProductDefinitionComparator;
import com.scw.devops.contract.store.common.data.ClientProductDefinitionFilters;
import com.scw.devops.contract.store.common.data.ClientProductDefinitionTransformer;
import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.command.GetEnvironmentsWithProductDeployedCommand;
import com.scw.devops.contract.store.query.command.ClientGetEnvironmentsWithProductDeployedCommand;
import com.scw.devops.contract.store.query.command.GetProductApplicationReferencesCommand;
import com.scw.devops.contract.store.query.command.ClientGetProductApplicationReferencesCommand;
import com.scw.devops.contract.store.query.data.ApplicationInstance;
import com.scw.devops.query.controller.request.StandardQueryInputFilter;
import com.scw.devops.query.controller.response.ProductDef;

@Component
public class ProductResolver {

	private final DataStoreReader reader;

	@Autowired
	public ProductResolver(final DataStoreReader reader) {
		this.reader = reader;
	}

	public List<ProductDef> filterProducts(final Collection<ProductDefinition> products, final Optional<Integer> versionLimit,
			final StandardQueryInputFilter queryFilter) {
		return products.stream()
			.filter( p -> ClientProductDefinitionFilters.filterByName( p, queryFilter.getWantedName() ) )
			.filter( p -> ClientProductDefinitionFilters.filterByErrorState( p, queryFilter.getWantedValidity() ) )
			.filter( e -> ClientProductDefinitionFilters
				.filterByExactString( e,
									  queryFilter
										  .getUnsetClassOnly(),
						queryFilter.getWantedClass()))
			.sorted( ( a, b ) -> ClientProductDefinitionComparator.compareWithNameAscThenVersionDesc( a,
																								b ) )
				.map(this::enrichProductWithDeployedEnvironments).map(this::enrichProductWithApplicationAlias)
				.map(p -> p.transformTo()).collect(toList());
	}

	private EnrichedProductDefinition enrichProductWithDeployedEnvironments(final ProductDefinition productDef) {
		EnrichedProductDefinition enrichedProductDef = new EnrichedProductDefinition();
		enrichedProductDef.productDefinition = productDef;
		GetEnvironmentsWithProductDeployedCommand command = new GetEnvironmentsWithProductDeployedCommand( productDef );
		enrichedProductDef.deployedToEnvironments = ClientGetEnvironmentsWithProductDeployedCommand.process( command, reader );
		Collections.sort( enrichedProductDef.deployedToEnvironments,
						  ( a, b ) -> ClientEnvironmentDefinitionComparator.compareWithNameAscThenVersionDesc( a, b ) );
		return enrichedProductDef;
	}

	private EnrichedProductDefinition enrichProductWithApplicationAlias(final EnrichedProductDefinition enrichedProductDef) {
		GetProductApplicationReferencesCommand command = new GetProductApplicationReferencesCommand( enrichedProductDef.productDefinition );
		enrichedProductDef.applicationInstsnces = ClientGetProductApplicationReferencesCommand.process( command, reader );
		return enrichedProductDef;
	}

	private static class EnrichedProductDefinition {
		private ProductDefinition productDefinition;
		private List<EnvironmentDefinition> deployedToEnvironments;
		private List<ApplicationInstance> applicationInstsnces;

		public ProductDef transformTo() {
			return ClientProductDefinitionTransformer.transformTo( productDefinition, deployedToEnvironments, applicationInstsnces );
		}
	}

}
