package com.scw.devops.contract.store.common.data;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProductDefinition {

	// Use containment otherwise we have to explicitly define constructor to pass
	// properties through
	final DefinitionBase base;
	final Collection<ApplicationInstanceEntry> applicationsInProduct;
}
