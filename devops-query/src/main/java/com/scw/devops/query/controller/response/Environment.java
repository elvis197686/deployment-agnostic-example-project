package com.scw.devops.query.controller.response;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Environment {
	@JsonProperty("name")
	final String name;
	@JsonProperty("version")
	final String version;
	@JsonProperty("definition")
	final EnvironmentDef definition;
	@JsonProperty("errors")
	final Collection<ConfigurationErrorData> errors;
}
