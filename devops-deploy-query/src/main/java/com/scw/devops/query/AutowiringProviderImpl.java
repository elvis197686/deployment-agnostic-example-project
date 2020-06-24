package com.scw.devops.query;

import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.query.application.autowiring.QueryAutowiring;
import com.scw.devops.query.service.ProductResolver;

public class AutowiringProviderImpl implements QueryAutowiring {

	private final DataStoreReader dataStoreReader;
	// Cannot make final as it refers to this implementation TODO - Inject reader directly instead of this indirection?
	private ProductResolver productResolver;

	public AutowiringProviderImpl( final DataStoreReader dataStoreReader ) {
		this.dataStoreReader = dataStoreReader;
	}

	public void fixup( final ProductResolver productResolver ) {
		this.productResolver = productResolver;
	}

	@Override
	public DataStoreReader getReader() {
		return dataStoreReader;
	}

	@Override
	public ProductResolver getProductResolver() {
		return productResolver;
	}

}
