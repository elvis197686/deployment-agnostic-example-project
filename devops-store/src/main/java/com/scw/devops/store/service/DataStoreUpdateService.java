package com.scw.devops.store.service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.ApplicationDefinitionProcessor;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntry;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntryProcessor;
import com.scw.devops.contract.store.common.data.EnhancedEnvironmentDefinition;
import com.scw.devops.contract.store.common.data.EnhancedEnvironmentProductDefinitionReference;
import com.scw.devops.contract.store.common.data.EnvironmentDefinitionProcessor;
import com.scw.devops.contract.store.common.data.MappableDefinitionReference;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.common.data.ProductDefinitionProcessor;
import com.scw.devops.store.state.DataStore;

@Component
public class DataStoreUpdateService {

	private final DataStore dataStore;

	public DataStoreUpdateService(final DataStore dataStore) {
		this.dataStore = dataStore;
	}

	public synchronized void addEnvironmentDefinition( final EnhancedEnvironmentDefinition p_enhancedEnvironmentDefinition ) throws Exception {
		this.dataStore.addEnvironmentDefinition( EnvironmentDefinitionProcessor.getName( p_enhancedEnvironmentDefinition ),
												 EnvironmentDefinitionProcessor.getVersion( p_enhancedEnvironmentDefinition ),
												 p_enhancedEnvironmentDefinition );
		recalculateEnvironmentsByProduct();
	}

	public synchronized void addProductDefinition( final ProductDefinition definition ) throws Exception {
		this.dataStore.addProductDefinition( ProductDefinitionProcessor.getName( definition ),
											 ProductDefinitionProcessor.getVersion( definition ),
											 definition );
		recalculateEnvironmentsByProduct();
		recalculateProductsByApplication();
	}

	public synchronized void addApplicationDefinition( final ApplicationDefinition definition ) {
		this.dataStore.addApplicationDefinition( ApplicationDefinitionProcessor.getName( definition ),
												 ApplicationDefinitionProcessor.getVersion( definition ),
												 definition );
		recalculateProductsByApplication();
	}

	private void recalculateEnvironmentsByProduct() {
		var environmentsByProduct = new HashMap<EnhancedEnvironmentProductDefinitionReference, List<EnhancedEnvironmentDefinition>>();

		// For each env get the products, then update or add product entry with env
		// definition
		var mergeEnvironmentsWithSameProduct = new BinaryOperator<List<EnhancedEnvironmentDefinition>>() {
			@Override
			public List<EnhancedEnvironmentDefinition> apply( final List<EnhancedEnvironmentDefinition> e1,
															  final List<EnhancedEnvironmentDefinition> e2 ) {
				e1.addAll(e2);
				return e1;
			}
		};
		this.dataStore.getCurrentEnvironmentList()
			.stream()
			.forEach( e -> EnvironmentDefinitionProcessor.getProductsInEnvironment( e )
				.stream()
				.forEach(p -> addToEnvironmentProductsMap(environmentsByProduct, p, e)));
		Map<MappableDefinitionReference, List<EnhancedEnvironmentDefinition>> environmentsByProductWithResolvedVersion = environmentsByProduct
			.entrySet()
			.stream()
				.map(p -> resolveProductVersionForEnvironment(p))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), mergeEnvironmentsWithSameProduct));

		this.dataStore.replaceEnvironmentsByProductMap(environmentsByProductWithResolvedVersion);
	}

	private Entry<MappableDefinitionReference, List<EnhancedEnvironmentDefinition>> resolveProductVersionForEnvironment( final Entry<EnhancedEnvironmentProductDefinitionReference, List<EnhancedEnvironmentDefinition>> entry ) {
		EnhancedEnvironmentProductDefinitionReference environmentProductReference = entry.getKey();
		MappableDefinitionReference productReference = environmentProductReference.getOrResolveWildcard( ( p ) -> dataStore.getProductLastTag( p ) );
		return new AbstractMap.SimpleEntry<MappableDefinitionReference, List<EnhancedEnvironmentDefinition>>( productReference,
				entry.getValue());
	}

	private void addToEnvironmentProductsMap(
											  final HashMap<EnhancedEnvironmentProductDefinitionReference, List<EnhancedEnvironmentDefinition>> environmentsByProduct,
			final EnhancedEnvironmentProductDefinitionReference p, final EnhancedEnvironmentDefinition e) {
		environmentsByProduct.compute(p, (k, v) -> {
			if (v == null) {
				v = new ArrayList<>();
			}
			v.add(e);
			return v;
		});
	}

	private void recalculateProductsByApplication() {
		var productsByApplication = new HashMap<MappableDefinitionReference, List<ProductDefinition>>();

		// For each env get the products, then update or add product entry with env
		// definition
		this.dataStore.getCurrentProductList()
			.stream()
			.forEach( p -> ProductDefinitionProcessor.getApplicationsInProduct( p )
				.stream()
				.forEach(a -> addToProductApplicationsMap(productsByApplication, a, p)));

		this.dataStore.replaceProductsByApplicationMap(productsByApplication);
	}

	private void addToProductApplicationsMap(
											  final Map<MappableDefinitionReference, List<ProductDefinition>> productsByApplication,
											  final ApplicationInstanceEntry applicationEntry, final ProductDefinition product ) {
		productsByApplication.compute( ApplicationInstanceEntryProcessor.fromApplicationEntry( applicationEntry ), ( k, v ) -> {
			if (v == null) {
				v = new ArrayList<>();
			}
			v.add(product);
			return v;
		});
	}
}
