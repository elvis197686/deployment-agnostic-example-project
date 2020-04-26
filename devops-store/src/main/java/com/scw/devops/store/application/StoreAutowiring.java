package com.scw.devops.store.application;

import com.scw.devops.application.AutowiringProvider;
import com.scw.devops.store.state.DataStore;

public abstract class StoreAutowiring {

	static StoreAutowiring decorator = null;

	static {
		// Must set up autowiring dynamically so we can switch between providers. Maybe use java SPI?
		// TODO - Any point not just autowiring locally?
		decorator = AutowiringProvider.getProvider( StoreAutowiring.class );
	}

	public DataStore getDataStore() {
		return decorator.getDataStore();
	}

}
