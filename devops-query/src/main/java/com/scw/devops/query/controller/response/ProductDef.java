package com.scw.devops.query.controller.response;

import java.util.List;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProductDef {

	@JsonProperty("name")
	final String name;
	@JsonProperty("version")
	final String version;

	@JsonProperty("productClass")
	@Nullable
	final String productClass;
	@JsonProperty("sourceRepository")
	final String sourceRepository;

	@JsonProperty("deployToEnvironments")
	final List<EnvironmentDef> deployToEnvironments;

	@JsonProperty("aliasedAppDefs")
	@Nullable
	final List<ApplicationAlias> aliasedAppDefs;

	@JsonProperty("error")
	@Nullable
	final ConfigurationErrorData error;
}
