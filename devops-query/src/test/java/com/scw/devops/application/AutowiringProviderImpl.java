package com.scw.devops.application;

import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.query.application.QueryAutowiring;
import com.scw.devops.query.service.ProductResolver;

public class AutowiringProviderImpl implements QueryAutowiring {

	private final DataStoreReader dataStoreReader;
	private final ProductResolver productResolver;

	public AutowiringProviderImpl( final DataStoreReader reader, final ProductResolver productResolver ) {
		this.dataStoreReader = reader;
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
