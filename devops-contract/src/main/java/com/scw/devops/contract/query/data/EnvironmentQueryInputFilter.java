package com.scw.devops.contract.query.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class EnvironmentQueryInputFilter {

	final Optional<String> name;
	final Optional<String> version;
	final Optional<Boolean> includePreview;
	final Optional<Boolean> autoStart;

}
