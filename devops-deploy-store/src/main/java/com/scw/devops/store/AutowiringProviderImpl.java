package com.scw.devops.store;

import com.scw.devops.store.application.StoreAutowiring;
import com.scw.devops.store.state.DataStore;

public class AutowiringProviderImpl implements StoreAutowiring {

	private final DataStore dataStore;

	public AutowiringProviderImpl( final DataStore dataStore ) {
		this.dataStore = dataStore;
	}

	@Override
	public DataStore getDataStore() {
		return dataStore;
	}

}
