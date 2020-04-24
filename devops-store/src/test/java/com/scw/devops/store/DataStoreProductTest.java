package com.scw.devops.store;

public class DataStoreProductTest {

	private final String DEFAULT_PROJECT_NAME = "projectName1";

	// TODO
	//	@Test
	//	public void shouldPutPreviewAtStart() throws Exception {
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		writer.addProductDefinition(productDefinitionWithVersion("0.3.0", false));
	//		writer.addProductDefinition(productDefinitionWithVersion("0.1.0", false));
	//		writer.addProductDefinition(productDefinitionWithVersion("preview", true));
	//		writer.addProductDefinition(productDefinitionWithVersion("0.4.0", false));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//		List<ProductDefinitionProcessor> resultingVersions = reader.getAllProductDefinitions(versionQueryWithPreview());
	//		Assert.assertThat(resultingVersions.size(), is(4));
	//		Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(1).base.version.getSingleVersionString(), is("0.4.0"));
	//		Assert.assertThat(resultingVersions.get(2).base.version.getSingleVersionString(), is("0.3.0"));
	//		Assert.assertThat(resultingVersions.get(3).base.version.getSingleVersionString(), is("0.1.0"));
	//	}
	//
	//	@Test
	//	public void shouldPutInvalidVersionsAtEnd() throws Exception {
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		writer.addProductDefinition(productDefinitionWithVersion("0.3.0", false));
	//		writer.addProductDefinition(productDefinitionWithVersion("0.1.0", false));
	//		writer.addProductDefinition(productDefinitionWithVersion("badVersion0.2.0", false));
	//		writer.addProductDefinition(productDefinitionWithVersion("0.4.0", false));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//		List<ProductDefinitionProcessor> resultingVersions = reader.getAllProductDefinitions(versionQueryWithPreview());
	//		Assert.assertThat(resultingVersions.size(), is(4));
	//		Assert.assertThat(resultingVersions.get(0).base.version.getSingleVersionString(), is("0.4.0"));
	//		Assert.assertThat(resultingVersions.get(1).base.version.getSingleVersionString(), is("0.3.0"));
	//		Assert.assertThat(resultingVersions.get(2).base.version.getSingleVersionString(), is("0.1.0"));
	//		Assert.assertThat(resultingVersions.get(3).base.version.getSingleVersionString(), is("badVersion0.2.0"));
	//	}
	//
	//	private ProductDefinitionProcessor productDefinitionWithVersion(String version, boolean isPreview) {
	//		return new ProductDefinitionProcessor(
	//				new DefinitionBase(DEFAULT_PROJECT_NAME, new ProjectVersion(version, isPreview), null, "arepo", null),
	//				null);
	//	}
	//
	//	private VersionQuery versionQueryWithPreview() {
	//		VersionQuery query = new VersionQuery();
	//		query.includePreview = true;
	//		query.versionLimit = 5;
	//		query.wantedVersionIsPreview = false;
	//		query.wantedVersionAsSemVer = empty();
	//		return query;
	//	}
}
