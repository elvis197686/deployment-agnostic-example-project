package com.scw.devops.contract.store.common.data;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class EnvironmentProductDefinitionReference {

	final String name;
	final ProjectVersion version;

}
