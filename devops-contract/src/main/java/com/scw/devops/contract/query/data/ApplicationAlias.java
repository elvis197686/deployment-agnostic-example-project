package com.scw.devops.contract.query.data;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ApplicationAlias {
	final String alias;
	final ApplicationDef definition;
}
