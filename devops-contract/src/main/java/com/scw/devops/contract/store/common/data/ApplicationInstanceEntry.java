package com.scw.devops.contract.store.common.data;

import com.scw.devops.domain.projectversion.ProjectVersion;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ApplicationInstanceEntry {

	final String alias;
	final String repoName;
	final ProjectVersion version;

}
