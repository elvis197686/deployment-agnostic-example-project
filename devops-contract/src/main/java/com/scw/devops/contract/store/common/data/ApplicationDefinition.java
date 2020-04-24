package com.scw.devops.contract.store.common.data;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ApplicationDefinition {

	// Use containment otherwise we have to explicitly define constructor to pass
	// properties through
	final DefinitionBase base;
}
