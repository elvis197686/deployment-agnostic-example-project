package com.scw.devops.test.integration.config;

import java.util.Arrays;
import java.util.Collection;

import com.scw.devops.collector.config.GitlabConfiguration;

public class GitlabConfigurationImpl implements GitlabConfiguration {

	public String getEnvironmentGroupName() {
		return "environments";
	}

	public String getProductGroupName() {
		return "products";
	}

	public Collection<String> getProductsToIgnore() {
		return Arrays.asList();
	}
}
