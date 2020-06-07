package com.scw.devops.contract.query.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ApplicationDef {
	final String name;
	final String version;
	final String sourceRepository;
	final String imageRepository;
	final String applicationRuntime;
	final Optional<ConfigurationErrorData> error;
}
