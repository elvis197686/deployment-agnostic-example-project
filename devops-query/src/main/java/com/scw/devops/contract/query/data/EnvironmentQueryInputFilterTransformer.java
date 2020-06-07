package com.scw.devops.contract.query.data;

import java.util.Optional;

import com.scw.devops.contract.store.query.data.VersionQuery;
import com.scw.devops.query.service.DataStoreQueryBuilder;

public class EnvironmentQueryInputFilterTransformer {

	public static VersionQuery transformTo( final EnvironmentQueryInputFilter queryFilter, final Optional<Integer> versionLimit ) {
		return DataStoreQueryBuilder.generateQuery( versionLimit, queryFilter.version, queryFilter.includePreview, true );
	}

}
