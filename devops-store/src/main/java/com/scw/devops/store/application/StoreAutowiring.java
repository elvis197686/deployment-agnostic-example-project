package com.scw.devops.store.application;

import com.scw.devops.store.state.DataStore;

public interface StoreAutowiring {

	public DataStore getDataStore();

}
