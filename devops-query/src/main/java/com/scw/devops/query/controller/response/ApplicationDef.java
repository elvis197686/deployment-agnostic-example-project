package com.scw.devops.query.controller.response;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ApplicationDef {
	@JsonProperty("name")
	final String name;
	@JsonProperty("version")
	final String version;
	@JsonProperty("sourceRepository")
	final String sourceRepository;
	@JsonProperty("imageRepository")
	final String imageRepository;
	@JsonProperty("runtime")
	final String applicationRuntime;
	@JsonProperty("error")
	@Nullable
	final ConfigurationErrorData error;
}
