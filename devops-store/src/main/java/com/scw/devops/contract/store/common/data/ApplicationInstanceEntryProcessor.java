package com.scw.devops.contract.store.common.data;

import com.scw.devops.store.service.ApplicationInstanceReference;

public class ApplicationInstanceEntryProcessor {

	public static MappableDefinitionReference fromApplicationEntry( final ApplicationInstanceEntry applicationEntry ) {
		return new MappableDefinitionReference( applicationEntry.repoName, applicationEntry.version );
	}

	public static ApplicationInstanceReference getAppRef( final ApplicationInstanceEntry entry ) {
		return new ApplicationInstanceReference( entry.repoName, SharedProjectVersionProcessor.getSingleVersionString( entry.version ) );
	}

	public static String getAlias( final ApplicationInstanceEntry entry ) {
		return entry.alias;
	}

}
