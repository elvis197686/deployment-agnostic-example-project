package com.scw.devops.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scw.devops.contract.store.DataStoreUpdateImpl;
import com.scw.devops.contract.store.update.DataStoreUpdater;
import com.scw.devops.store.application.StoreAutowiring;
import com.scw.devops.store.service.DataStoreQueryService;
import com.scw.devops.store.service.DataStoreUpdateService;
import com.scw.devops.store.state.DataStore;

@Configuration
public class AutowiringConfig {

	@Bean
	public DataStoreUpdater dataStoreUpdate() {
		return new DataStoreUpdateImpl( dataStoreUpdateService() );
	}

	@Bean
	public DataStoreUpdateService dataStoreUpdateService() {
		return new DataStoreUpdateService( storeAutowiring() );
	}

	@Bean
	public DataStoreQueryService dataStoreQueryService() {
		return new DataStoreQueryService( storeAutowiring() );
	}

	public StoreAutowiring storeAutowiring() {
		return new AutowiringProviderImpl( dataStore() );
	}

	public DataStore dataStore() {
		return new DataStore();
	}
}
