package com.scw.devops.application;

import com.scw.devops.store.application.StoreAutowiring;
import com.scw.devops.store.state.DataStore;

public class AutowiringProviderImpl extends StoreAutowiring {

	public static AutowiringProviderImpl getProvider( final Class<AutowiringProviderImpl> clazz ) {
		return new AutowiringProviderImpl();
	}

	@Override
	public DataStore getDataStore() {
		return accessAutowiring();
	}

}
