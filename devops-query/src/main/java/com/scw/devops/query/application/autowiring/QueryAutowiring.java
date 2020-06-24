package com.scw.devops.query.application.autowiring;

import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.query.service.ProductResolver;

public interface QueryAutowiring {

	public DataStoreReader getReader();

	public ProductResolver getProductResolver();

}
