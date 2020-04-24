package com.scw.devops.collector.config;

import static org.apache.commons.lang3.Validate.notBlank;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.scw.devops.collector.config.GitlabConfiguration;

@Configuration
public class GitlabConfigurationImpl implements GitlabConfiguration {

	private static final String ENVIRONMENT_GROUP_NAME_IS_MISSING = "Gitlab environment group name is missing";
	private static final String PRODUCT_GROUP_NAME_IS_MISSING = "Gitlab product group name is missing";

	private final String environmentGroup;
	private final String productGroup;
	private final Collection<String> productsToIgnore;

	@Autowired
	public GitlabConfigurationImpl( @Value( "${gitlab.environment-group}" ) final String environmentGroup,
			@Value("${gitlab.product-group}") final String productGroup,
			@Value("${gitlab.products-to-ignore}") final String productsToIgnore) {
		notBlank(environmentGroup, ENVIRONMENT_GROUP_NAME_IS_MISSING);
		notBlank(productGroup, PRODUCT_GROUP_NAME_IS_MISSING);
		this.environmentGroup = environmentGroup;
		this.productGroup = productGroup;
		this.productsToIgnore = productsToIgnore == null ? Arrays.asList()
				: Arrays.stream(productsToIgnore.split(" ")).collect(Collectors.toList());
	}

	public String getEnvironmentGroupName() {
		return environmentGroup;
	}

	public String getProductGroupName() {
		return productGroup;
	}

	public Collection<String> getProductsToIgnore() {
		return productsToIgnore;
	}
}
