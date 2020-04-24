package com.scw.devops.contract.store.common.data;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProjectVersion {

	final String version;
	final boolean isPreview;

}
