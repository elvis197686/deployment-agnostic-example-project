package com.scw.devops.contract.store.common.data;

import com.scw.devops.store.service.ApplicationInstanceReferenceQuery;

public class ApplicationInstanceEntryProcessor {

	public static MappableDefinitionReference fromApplicationEntry( final ApplicationInstanceEntry applicationEntry ) {
		return new MappableDefinitionReference( applicationEntry.repoName, applicationEntry.version );
	}

	public static ApplicationInstanceReferenceQuery getAppRef( final ApplicationInstanceEntry entry ) {
		return new ApplicationInstanceReferenceQuery( entry.repoName, null, entry.version );
	}

	public static String getAlias( final ApplicationInstanceEntry entry ) {
		return entry.alias;
	}

}
