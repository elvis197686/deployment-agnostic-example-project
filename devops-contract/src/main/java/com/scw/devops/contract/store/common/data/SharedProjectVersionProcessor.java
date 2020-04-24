package com.scw.devops.contract.store.common.data;

public class SharedProjectVersionProcessor {

	private static final String PREVIEW_VERSION = "preview";

	public static String getSingleVersionString( final ProjectVersion version ) {
		return ( version.isPreview ) ? PREVIEW_VERSION : version.version;
	}

}
