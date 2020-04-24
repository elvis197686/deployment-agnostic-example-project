package com.scw.devops.contract.store.query.data;

import com.scw.devops.contract.store.common.data.ApplicationDefinition;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ApplicationInstance {

	final String alias;
	final ApplicationDefinition applicationDefinition;
}
