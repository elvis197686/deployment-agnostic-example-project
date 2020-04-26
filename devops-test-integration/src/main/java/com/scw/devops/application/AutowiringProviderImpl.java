package com.scw.devops.application;

import com.scw.devops.store.state.DataStore;

public class AutowiringProviderImpl implements AutowiringProvider {

	@Override
	public DataStore getDataStore() {
		return new DataStore();
	}

}
