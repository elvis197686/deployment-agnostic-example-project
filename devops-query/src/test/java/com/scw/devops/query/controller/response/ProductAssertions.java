package com.scw.devops.query.controller.response;

import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;

import com.scw.devops.query.controller.response.ProductDef;

public class ProductAssertions {

	public static void assertProductName(ProductDef product, String expectedName) {
		MatcherAssert.assertThat(product.name, is(expectedName));
	}

	public static void assertProductNameAndVersion(ProductDef product, String expectedName,
			String expectedVersionString) {
		MatcherAssert.assertThat(product.name, is(expectedName));
		MatcherAssert.assertThat(product.version, is(expectedVersionString));
	}

	public static void assertProductHasDeployedEnvironmentsInOrder(ProductDef productDef,
			List<NameAndVersion> expectedOrder) {
		Assert.assertThat(productDef.deployToEnvironments.size(), is(expectedOrder.size()));
		for (int i = 0; i < productDef.deployToEnvironments.size(); i++) {
			Assert.assertThat(productDef.deployToEnvironments.get(i).name, is(expectedOrder.get(i).name));
			Assert.assertThat(productDef.deployToEnvironments.get(i).version, is(expectedOrder.get(i).version));
		}
	}

	public static void assertProductHasApplications(ProductDef productDef, List<AliasAndName> expectedItems) {
		Assert.assertThat(productDef.aliasedAppDefs.size(), is(expectedItems.size()));
		for (int i = 0; i < productDef.aliasedAppDefs.size(); i++) {
			Assert.assertThat(productDef.aliasedAppDefs.get(i).definition.name, is(expectedItems.get(i).name));
			Assert.assertThat(productDef.aliasedAppDefs.get(i).alias, is(expectedItems.get(i).alias));
		}
	}

	public static class NameAndVersion {
		private final String name;
		private final String version;

		public NameAndVersion(String name, String version) {
			this.name = name;
			this.version = version;
		}
	}

	public static class AliasAndName {
		private final String name;
		private final String alias;

		public AliasAndName(String alias, String name) {
			this.alias = alias;
			this.name = name;
		}
	}
}
