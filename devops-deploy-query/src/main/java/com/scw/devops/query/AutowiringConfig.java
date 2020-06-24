package com.scw.devops.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scw.devops.contract.store.DataStoreReaderImpl;
import com.scw.devops.contract.store.RestGateway;
import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.query.application.autowiring.QueryAutowiring;
import com.scw.devops.query.service.DefinitionQuery;
import com.scw.devops.query.service.ProductResolver;

@Configuration
public class AutowiringConfig {

	@Autowired
	RestGateway restGateway;

	@Bean
	public DefinitionQuery definitionQuery() {
		return new DefinitionQuery( queryAutowiring() );
	}

	public QueryAutowiring queryAutowiring() {
		AutowiringProviderImpl autowiring = new AutowiringProviderImpl( dataStoreReader() );
		autowiring.fixup( new ProductResolver( autowiring ) );
		return autowiring;
	}

	// TODO - bean annotation needed?
	@Bean
	public DataStoreReader dataStoreReader() {
		return new DataStoreReaderImpl( restGateway );
	}
}
