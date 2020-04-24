package com.scw.devops.query.service;

import static com.scw.devops.query.controller.DataStoreQueryBuilder.generateQuery;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.ClientApplicationDefinitionComparator;
import com.scw.devops.contract.store.common.data.ClientApplicationDefinitionFilters;
import com.scw.devops.contract.store.common.data.ClientApplicationDefinitionProcessor;
import com.scw.devops.contract.store.common.data.ClientApplicationDefinitionTransformer;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.ClientEnvironmentDefinitionFilters;
import com.scw.devops.contract.store.common.data.ClientEnvironmentDefinitionTransformer;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.command.GetAllApplicationDefinitionsCommand;
import com.scw.devops.contract.store.query.command.ClientGetAllApplicationDefinitionsCommand;
import com.scw.devops.contract.store.query.command.GetAllEnvironmentDefinitionsCommand;
import com.scw.devops.contract.store.query.command.ClientGetAllEnvironmentDefinitionsCommand;
import com.scw.devops.contract.store.query.command.GetAllProductDefinitionsCommand;
import com.scw.devops.contract.store.query.command.ClientGetAllProductDefinitionsCommand;
import com.scw.devops.contract.store.query.command.GetApplicationDefinitionCommand;
import com.scw.devops.contract.store.query.command.ClientGetApplicationDefinitionCommand;
import com.scw.devops.contract.store.query.command.GetPreviousApplicationDefinitionCommand;
import com.scw.devops.contract.store.query.command.ClientGetPreviousApplicationDefinitionCommand;
import com.scw.devops.contract.store.query.command.GetProductDefinitionsForApplicationCommand;
import com.scw.devops.contract.store.query.command.ClientGetProductDefinitionsForApplicationCommand;
import com.scw.devops.contract.store.query.command.GetProductDefinitionsForEnvironmentCommand;
import com.scw.devops.contract.store.query.command.ClientGetProductDefinitionsForEnvironmentCommand;
import com.scw.devops.contract.store.query.data.VersionQuery;
import com.scw.devops.query.controller.request.EnvironmentQueryInputFilter;
import com.scw.devops.query.controller.request.StandardQueryInputFilter;
import com.scw.devops.query.controller.response.ApplicationDef;
import com.scw.devops.query.controller.response.Environment;
import com.scw.devops.query.controller.response.ProductDef;

@Service
public class DefinitionQuery {

	private final DataStoreReader reader;
	private final ProductResolver productResolver;

	@Autowired
	public DefinitionQuery(final DataStoreReader reader, final ProductResolver productResolver) {
		this.reader = reader;
		this.productResolver = productResolver;
	}

	public List<Environment> getEnvironments(final Optional<Integer> versionLimit, final EnvironmentQueryInputFilter queryFilter) {
		// Simple loop of all known environments
		// Doing some query code (i.e. filtering) here to minimise development overhead
		// when adding new properties
		GetAllEnvironmentDefinitionsCommand command = new GetAllEnvironmentDefinitionsCommand( queryFilter.transformTo( versionLimit ) );
		Collection<EnvironmentDefinition> environments = ClientGetAllEnvironmentDefinitionsCommand.process( command, reader );
		return environments.stream()
			.filter( e -> ClientEnvironmentDefinitionFilters.filterByName( e, queryFilter.getWantedName() ) )
			.filter( e -> ClientEnvironmentDefinitionFilters.filterByBooleanProperty( e, queryFilter.isAutoStartWanted() ) )
			.map( e -> ClientEnvironmentDefinitionTransformer.transformTo( e ) )
			.collect( Collectors.toList() );
	}

	public List<ProductDef> getProducts(final Optional<Integer> versionLimit, final StandardQueryInputFilter queryFilter) {
		GetAllProductDefinitionsCommand command = new GetAllProductDefinitionsCommand( queryFilter.transformTo( versionLimit ) );
		Collection<ProductDefinition> products = ClientGetAllProductDefinitionsCommand.process( command, reader );
		return productResolver.filterProducts(products, versionLimit, queryFilter);
	}

	public List<ApplicationDef> getApplications(final Optional<Integer> versionLimit, final StandardQueryInputFilter queryFilter) {
		GetAllApplicationDefinitionsCommand command = new GetAllApplicationDefinitionsCommand( queryFilter.transformTo( versionLimit ) );
		Collection<ApplicationDefinition> applications = ClientGetAllApplicationDefinitionsCommand.process( command, reader );
		// Note: class query is not relevant
		return applications.stream()
			.filter( a -> ClientApplicationDefinitionFilters
				.filterByName( a,
							   queryFilter
								   .getWantedName() ) )
			.filter( a -> ClientApplicationDefinitionFilters
				.filterByErrorState( a,
									 queryFilter
										 .getWantedValidity() ) )
			.sorted( ( a, b ) -> ClientApplicationDefinitionComparator.compareWithNameAscThenVersionDesc( a, b ) )
			.map( a -> ClientApplicationDefinitionTransformer.transformTo( a ) )
			.collect( toList() );
	}

	public Collection<ProductDef> getProductsInEnvironment(final String name, final String version, final Optional<String> wantedClass) {
		GetProductDefinitionsForEnvironmentCommand command = new GetProductDefinitionsForEnvironmentCommand( name, version );
		Collection<ProductDefinition> products = ClientGetProductDefinitionsForEnvironmentCommand.process( command, reader );
		return productResolver.filterProducts(products, Optional.empty(),
				new StandardQueryInputFilter(null, null, null, null, wantedClass.orElse(null)));
	}

	public Collection<ProductDef> getProductsInApplication(final String applicationName, final String version,
			final Optional<Integer> versionLimit, final StandardQueryInputFilter queryFilter) {
		GetProductDefinitionsForApplicationCommand command = new GetProductDefinitionsForApplicationCommand( applicationName,
																											 version,
																											 queryFilter
																												 .transformTo( versionLimit ) );
		Collection<ProductDefinition> products = ClientGetProductDefinitionsForApplicationCommand.process( command, reader );
		return productResolver.filterProducts(products, versionLimit, queryFilter);
	}

	public ApplicationDef getPreviousApplicationDefinition(final String name, final String version) {
		GetApplicationDefinitionCommand command = new GetApplicationDefinitionCommand( name, version );
		Optional<ApplicationDefinition> applicationForPrevious = ClientGetApplicationDefinitionCommand.process( command, reader );
		if (applicationForPrevious.isPresent()) {
			GetPreviousApplicationDefinitionCommand optCommand = new GetPreviousApplicationDefinitionCommand( applicationForPrevious.get() );
			return ClientGetPreviousApplicationDefinitionCommand.process( optCommand, reader )
				.map( a -> ClientApplicationDefinitionTransformer.transformTo( a ) )
				.orElse( null );
		}
		return null;
	}

	public List<ApplicationDef> getOrphanedApplicationDefinitionsSinceRestart() {
		VersionQuery previewOnlyVersionQuery = generateQuery(Optional.of(1), null, true, true);
		GetAllApplicationDefinitionsCommand command = new GetAllApplicationDefinitionsCommand( previewOnlyVersionQuery );
		Collection<ApplicationDefinition> applications = ClientGetAllApplicationDefinitionsCommand.process( command, reader );
		return applications.stream()
			.filter( a -> anyProductsForApplication( a, previewOnlyVersionQuery ) )
			.sorted( ( a, b ) -> ClientApplicationDefinitionComparator.compareWithNameAscThenVersionDesc( a, b ) )
			.map( a -> ClientApplicationDefinitionTransformer.transformTo(
																	 a ) )
			.collect( toList() );
	}

	private boolean anyProductsForApplication(final ApplicationDefinition a, final VersionQuery previewOnlyVersionQuery) {
		GetProductDefinitionsForApplicationCommand command = new GetProductDefinitionsForApplicationCommand( ClientApplicationDefinitionProcessor
			.getName( a ), "preview", previewOnlyVersionQuery );
		return ClientGetProductDefinitionsForApplicationCommand.process( command, reader ).size() == 0;
	}
}
