package com.scw.devops.query.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ApplicationAlias {
	@JsonProperty("alias")
	final String alias;
	@JsonProperty("definition")
	final ApplicationDef definition;
}
