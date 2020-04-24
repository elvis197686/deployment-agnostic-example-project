package com.scw.devops.query.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ConfigurationErrorData {
	@JsonProperty("message")
	final String message;
}
