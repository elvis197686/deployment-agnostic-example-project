package com.scw.devops.contract.query.data;

import java.util.Optional;

import com.scw.devops.contract.query.data.StandardQueryInputFilter;
import com.scw.devops.contract.store.query.data.VersionQuery;
import com.scw.devops.query.service.DataStoreQueryBuilder;

public class StandardQueryInputFilterTransformer {

	public static VersionQuery transformTo( final StandardQueryInputFilter queryFilter, final Optional<Integer> versionLimit ) {
		return DataStoreQueryBuilder.generateQuery( versionLimit, queryFilter.version, queryFilter.includePreview, false );
	}

}
