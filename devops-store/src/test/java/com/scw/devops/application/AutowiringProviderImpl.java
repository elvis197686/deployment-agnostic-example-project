package com.scw.devops.application;

import com.scw.devops.store.application.StoreAutowiring;
import com.scw.devops.store.state.DataStore;

public class AutowiringProviderImpl implements StoreAutowiring {

	private DataStore dataStore = new DataStore();

	@Override
	public DataStore getDataStore() {
		return dataStore;
	}

}
