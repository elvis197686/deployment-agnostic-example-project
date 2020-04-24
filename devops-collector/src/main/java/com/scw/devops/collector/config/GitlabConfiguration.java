package com.scw.devops.collector.config;

import java.util.Collection;

public interface GitlabConfiguration {

	public String getEnvironmentGroupName();

	public String getProductGroupName();

	public Collection<String> getProductsToIgnore();
}
