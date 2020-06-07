package com.scw.devops.contract.query.data;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class StandardQueryInputFilter {

	final Optional<String> name;
	final Optional<String> version;
	final Optional<Boolean> includePreview;
	final Optional<String> validity;
	final Optional<String> productClass;

}
