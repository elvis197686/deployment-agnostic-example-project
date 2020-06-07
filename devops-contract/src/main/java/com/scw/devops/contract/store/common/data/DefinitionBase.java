package com.scw.devops.contract.store.common.data;

import java.util.Collection;
import java.util.Map;

import com.scw.devops.domain.projectversion.ProjectVersion;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class DefinitionBase {
	final String name;
	final ProjectVersion version;
	final Map<String, Object> arbitraryProperties;
	final String sourceRepository;
	final Collection<ConfigurationError> errors;

}
