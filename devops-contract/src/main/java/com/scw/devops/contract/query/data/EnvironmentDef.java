package com.scw.devops.contract.query.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class EnvironmentDef {
	final String name;
	final String version;
	final Optional<Boolean> autoStart;
	final String sourceRepository;
	final Optional<ConfigurationErrorData> error;

}
