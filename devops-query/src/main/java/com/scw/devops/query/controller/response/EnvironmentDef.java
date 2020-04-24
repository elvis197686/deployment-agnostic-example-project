package com.scw.devops.query.controller.response;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class EnvironmentDef {
	@JsonProperty("name")
	final String name;
	@JsonProperty("version")
	final String version;
	@JsonProperty("autoStart")
	@Nullable
	final Boolean autoStart;
	@JsonProperty("sourceRepository")
	final String sourceRepository;
	@JsonProperty("error")
	@Nullable
	final ConfigurationErrorData error;

}
