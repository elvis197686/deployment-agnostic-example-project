package com.scw.devops.contract.query.data;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Environment {
	final String name;
	final String version;
	final EnvironmentDef definition;
	final Collection<ConfigurationErrorData> errors;
}
