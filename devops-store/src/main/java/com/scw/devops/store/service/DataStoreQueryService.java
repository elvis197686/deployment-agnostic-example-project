package com.scw.devops.store.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.ApplicationDefinitionProcessor;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntry;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntryProcessor;
import com.scw.devops.contract.store.common.data.EnhancedEnvironmentDefinition;
import com.scw.devops.contract.store.common.data.EnhancedEnvironmentProductDefinitionReference;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.MappableSortableProjectVersion;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.common.data.ProductDefinitionProcessor;
import com.scw.devops.contract.store.common.data.ProjectVersionProcessor;
import com.scw.devops.contract.store.query.data.ApplicationInstance;
import com.scw.devops.contract.store.query.data.VersionQuery;
import com.scw.devops.contract.store.query.data.VersionQueryProcessor;
import com.scw.devops.store.application.StoreAutowiring;
import com.scw.devops.store.state.DataStore;

public class DataStoreQueryService {

	private final DataStore dataStore;

	public DataStoreQueryService( final StoreAutowiring autowiring ) {
		this.dataStore = autowiring.getDataStore();
	}

	public List<EnvironmentDefinition> getAllEnvironmentDefinitions(final VersionQuery versionQuery) {
		return dataStore.getEnvironmentsByVersionByName()
			.entrySet()
			.stream()
			.flatMap( e -> sortAndKeepVersions( e.getValue(), versionQuery ) )
			.map( e -> restoreOriginalEnvironment( e ) )
			.collect( Collectors.toList() );
	}

	public List<ProductDefinition> getAllProductDefinitions( final VersionQuery versionQuery ) {
		return dataStore.getProductsByVersionByName()
			.entrySet()
			.stream()
				.flatMap(e -> sortAndKeepVersions(e.getValue(), versionQuery)).collect(Collectors.toList());
	}

	public List<ApplicationDefinition> getAllApplicationDefinitions(final VersionQuery versionQuery) {
		return dataStore.getApplicationsByVersionByName()
			.entrySet()
			.stream()
				.flatMap(e -> sortAndKeepVersions(e.getValue(), versionQuery)).collect(Collectors.toList());
	}

	public Optional<ApplicationDefinition> getApplicationDefinition(final String name, final String version) {
		Map<MappableSortableProjectVersion, ApplicationDefinition> foundVersionList = dataStore.getApplicationsByVersionByName().get( name );
		if (foundVersionList != null) {
			return Optional.ofNullable( foundVersionList.get( ProjectVersionProcessor.mappableFromSingleString( version ) ) );
		}
		return Optional.empty();
	}

	public List<EnvironmentDefinition> getEnvironmentsWithProductDeployed( final ProductDefinition productDef ) {
		// Only preview environments are considered
		return dataStore
			.getEnvironmentsForProduct( ProductDefinitionProcessor.getDefinitionReference( productDef ) )
			.stream()
			.filter( e -> e.isPreviewVersion() )
			.map( e -> e.getOriginalDefinition() )
			.collect( Collectors.toList() );
	}

	public List<ProductDefinition> getProductDefinitionsForEnvironment( final String name,
			final String wantedVersionAsSingleString) {
		Optional<EnhancedEnvironmentDefinition> foundEnvironment = getEnvironment( name, wantedVersionAsSingleString );
		if (foundEnvironment.isPresent()) {
			return foundEnvironment.get()
				.getProductsInEnvironment()
				.stream()
					.map(p -> getProductWithResolvedVersionFromEntry(p)).filter(o -> o.isPresent()).map(o -> o.get())
					.collect(Collectors.toList());
		}
		return Arrays.asList();
	}

	public List<ProductDefinition> getProductDefinitionsForApplication( final String name, final String wantedVersionAsSingleString,
			final VersionQuery productVersionsToReturn) {
		ApplicationInstanceReferenceQuery ref = new ApplicationInstanceReferenceQuery( name, wantedVersionAsSingleString, null );
		Optional<ApplicationDefinition> foundApplication = ref.getApplication( dataStore.getApplicationsByVersionByName() );
		if (foundApplication.isPresent()) {
			return dataStore.getProductsForApplication(
														ApplicationDefinitionProcessor.getDefinitionReference( foundApplication.get() ) );
		}
		return Arrays.asList();
	}

	public List<ApplicationInstance> getProductApplicationReferences( final ProductDefinition productDefinition ) {
		Collection<ApplicationInstanceEntry> applications = ProductDefinitionProcessor.getApplicationsInProduct( productDefinition );
		return applications.stream().map(a -> getApplicationFromEntry(a)).filter(o -> o.isPresent()).map(o -> o.get())
				.collect(Collectors.toList());
	}

	private EnvironmentDefinition restoreOriginalEnvironment( final EnhancedEnvironmentDefinition e ) {
		return e.getOriginalDefinition();
	}

	private Optional<EnhancedEnvironmentDefinition> getEnvironment( final String name, final String versionAsSingleString ) {
		Map<MappableSortableProjectVersion, EnhancedEnvironmentDefinition> environmentDefinitionsByVersion = dataStore
			.getEnvironmentsByVersionByName()
				.get(name);
		if (environmentDefinitionsByVersion != null) {
			return Optional.ofNullable(environmentDefinitionsByVersion
				.getOrDefault( ProjectVersionProcessor.fromQueryVersionString( versionAsSingleString ), null ) );
		}
		return Optional.empty();
	}

	private Optional<ProductDefinition> getProductWithResolvedVersionFromEntry(
																				final EnhancedEnvironmentProductDefinitionReference entry ) {
		ProductDefinitionReferenceQuery ref = entry.getProductDefinitionReference( ( p ) -> dataStore.getProductLastTag( p ) );
		return ref.getProduct( dataStore.getProductsByVersionByName() );
	}

	private Optional<ApplicationInstance> getApplicationFromEntry( final ApplicationInstanceEntry entry ) {
		ApplicationInstanceReferenceQuery ref = ApplicationInstanceEntryProcessor.getAppRef( entry );
		Optional<ApplicationDefinition> definition = ref.getApplication( dataStore.getApplicationsByVersionByName() );
		if (definition.isPresent()) {
			return Optional.of( new ApplicationInstance( ApplicationInstanceEntryProcessor.getAlias( entry ), definition.get() ) );
		}
		return Optional.empty();
	}

	public Optional<ApplicationDefinition> getPreviousApplicationDefinition(
			final ApplicationDefinition applicationDefinition) {
		List<MappableSortableProjectVersion> sortedVersions = dataStore.getApplicationsByVersionByName()
			.get( ApplicationDefinitionProcessor.getName(
														  applicationDefinition ) )
				.entrySet().stream().map(a -> a.getKey()).sorted((a, b) -> a.compareTo(b)).collect(Collectors.toList());
		int indexOfApplication = sortedVersions.indexOf( ApplicationDefinitionProcessor.getVersion( applicationDefinition ) );
		if (indexOfApplication >= 0 && (indexOfApplication + 1) < sortedVersions.size()) {
			return Optional.ofNullable( dataStore.getApplicationsByVersionByName()
				.get( ApplicationDefinitionProcessor.getName( applicationDefinition ) )
					.get(sortedVersions.get(indexOfApplication + 1)));
		}
		return Optional.empty();
	}

	private <T> Stream<T> sortAndKeepVersions( final Map<MappableSortableProjectVersion, T> entriesByVersion, final VersionQuery versionQuery ) {
		List<Entry<MappableSortableProjectVersion, T>> sortedByVersion = entriesByVersion.entrySet()
			.stream()
				.sorted((a, b) -> a.getKey().compareTo(b.getKey())).collect(Collectors.toList());
		List<T> entriesMatchingVersionRequirements = new ArrayList<>();
		for ( Entry<MappableSortableProjectVersion, T> environmentByVersion : sortedByVersion ) {
			if ( VersionQueryProcessor.includeThisVersion( versionQuery, environmentByVersion.getKey() ) ) {
				entriesMatchingVersionRequirements.add(environmentByVersion.getValue());
				if ( VersionQueryProcessor.hasMaximumVersions( versionQuery, entriesMatchingVersionRequirements.size() ) ) {
					break;
				}
			}
		}
		return entriesMatchingVersionRequirements.stream();
	}
}
