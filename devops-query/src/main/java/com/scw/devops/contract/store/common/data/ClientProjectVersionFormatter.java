package com.scw.devops.contract.store.common.data;

import java.util.Optional;

import com.scw.devops.domain.projectversion.ProjectVersion;

public class ClientProjectVersionFormatter {

	private static final String PREVIEW_VERSION = "preview";

	public static String getSingleVersionString( final ProjectVersion version ) {
		Optional<String> versionName = version.getVersionNameIfNotPreview();
		return versionName.orElse( PREVIEW_VERSION );
	}

}
