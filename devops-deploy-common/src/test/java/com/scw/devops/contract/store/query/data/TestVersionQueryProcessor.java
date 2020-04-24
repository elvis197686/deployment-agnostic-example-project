package com.scw.devops.contract.store.query.data;


public class TestVersionQueryProcessor {

	public static int getLimit( final VersionQuery version ) {
		return version.versionLimit;
	}

}
