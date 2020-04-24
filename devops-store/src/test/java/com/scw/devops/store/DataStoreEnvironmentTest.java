package com.scw.devops.store;

public class DataStoreEnvironmentTest {

	private static final String DEFAULT_PROJECT_NAME = "projectName1";

	private static final String ENV_1_NAME = "env1";
	private static final String ENV_2_NAME = "env2";

	private static final String PRODUCT_1_NAME = "product1";
	private static final String PRODUCT_2_NAME = "product2";
	private static final String PRODUCT_3_NAME = "product3";

	// TODO
	//	@Test
	//	public void shouldPutPreviewAtStart() throws Exception {
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		writer.addEnvironmentDefinition(environmentDefinitionWithVersion("0.3.0", false));
	//		writer.addEnvironmentDefinition(environmentDefinitionWithVersion("0.1.0", false));
	//		writer.addEnvironmentDefinition(environmentDefinitionWithVersion("preview", true));
	//		writer.addEnvironmentDefinition(environmentDefinitionWithVersion("0.4.0", false));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//		List<EnvironmentDefinition> resultingVersions = reader.getAllEnvironmentDefinitions(versionQueryWithPreview());
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
	//		writer.addEnvironmentDefinition(environmentDefinitionWithVersion("0.3.0", false));
	//		writer.addEnvironmentDefinition(environmentDefinitionWithVersion("0.1.0", false));
	//		writer.addEnvironmentDefinition(environmentDefinitionWithVersion("badVersion0.2.0", false));
	//		writer.addEnvironmentDefinition(environmentDefinitionWithVersion("0.4.0", false));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//		List<EnvironmentDefinition> resultingVersions = reader.getAllEnvironmentDefinitions(versionQueryWithPreview());
	//		Assert.assertThat(resultingVersions.size(), is(4));
	//		Assert.assertThat(resultingVersions.get(0).base.version.getSingleVersionString(), is("0.4.0"));
	//		Assert.assertThat(resultingVersions.get(1).base.version.getSingleVersionString(), is("0.3.0"));
	//		Assert.assertThat(resultingVersions.get(2).base.version.getSingleVersionString(), is("0.1.0"));
	//		Assert.assertThat(resultingVersions.get(3).base.version.getSingleVersionString(), is("badVersion0.2.0"));
	//	}
	//
	//	@Test
	//	public void shouldReturnEnvironmentsWithGivenProduct() throws Exception {
	//		ProductDefinitionProcessor product1 = productDefinitionWithVersion(PRODUCT_1_NAME, "preview", true);
	//		ProductDefinitionProcessor product2 = productDefinitionWithVersion(PRODUCT_2_NAME, "preview", true);
	//		ProductDefinitionProcessor product3 = productDefinitionWithVersion(PRODUCT_3_NAME, "preview", true);
	//		DataStore.ProductDefinitionReference product1Entry = DataStore.ProductDefinitionReference
	//				.fromProductDefinition(product1);
	//		DataStore.ProductDefinitionReference product2Entry = DataStore.ProductDefinitionReference
	//				.fromProductDefinition(product2);
	//		DataStore.ProductDefinitionReference product3Entry = DataStore.ProductDefinitionReference
	//				.fromProductDefinition(product3);
	//
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		writer.addEnvironmentDefinition(environmentDefinitionWithProducts(ENV_1_NAME, "preview", true,
	//				productEntrysFor(product1Entry, product2Entry)));
	//		writer.addEnvironmentDefinition(environmentDefinitionWithProducts(ENV_2_NAME, "preview", true,
	//				productEntrysFor(product1Entry, product3Entry)));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//
	//		List<EnvironmentDefinition> resultingVersions = reader.getEnvironmentsWithProductDeployed(product1);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(2));
	//		Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is(ENV_1_NAME));
	//		Assert.assertThat(resultingVersions.get(1).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(1).base.name, is(ENV_2_NAME));
	//
	//		resultingVersions = reader.getEnvironmentsWithProductDeployed(product2);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(1));
	//		Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is(ENV_1_NAME));
	//
	//		resultingVersions = reader.getEnvironmentsWithProductDeployed(product3);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(1));
	//		Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is(ENV_2_NAME));
	//	}
	//
	//	@Test
	//	public void shouldReturnEnvironmentsWithGivenProductUsingLatestTag() throws Exception {
	//		ProductDefinitionProcessor product1Preview = productDefinitionWithVersion(PRODUCT_1_NAME, "preview", true);
	//		ProductDefinitionProcessor product1LastTag = productDefinitionWithVersion(PRODUCT_1_NAME, "0.2.0", false);
	//		ProductDefinitionProcessor product1Old = productDefinitionWithVersion(PRODUCT_1_NAME, "0.1.0", false);
	//		// Must store the entries to allow lookup on read
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		writer.addProductDefinition(product1Preview);
	//		writer.addProductDefinition(product1LastTag);
	//		writer.addProductDefinition(product1Old);
	//
	//		writer.addEnvironmentDefinition(environmentDefinitionWithProducts(ENV_1_NAME, "preview", true,
	//				productsEntryWithWildcard(PRODUCT_1_NAME)));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//
	//		List<EnvironmentDefinition> resultingVersions = reader.getEnvironmentsWithProductDeployed(product1Preview);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(0));
	//
	//		resultingVersions = reader.getEnvironmentsWithProductDeployed(product1LastTag);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(1));
	//		Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is(ENV_1_NAME));
	//
	//		resultingVersions = reader.getEnvironmentsWithProductDeployed(product1Old);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(0));
	//	}
	//
	//	@Test
	//	public void shouldReturnMultipleEnvironmentsWithGivenProductTag() throws Exception {
	//		ProductDefinitionProcessor product1Preview = productDefinitionWithVersion(PRODUCT_1_NAME, "preview", true);
	//		ProductDefinitionProcessor product1LastTag = productDefinitionWithVersion(PRODUCT_1_NAME, "0.2.0", false);
	//		// Must store the entries to allow lookup on read
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		writer.addProductDefinition(product1Preview);
	//		writer.addProductDefinition(product1LastTag);
	//
	//		writer.addEnvironmentDefinition(environmentDefinitionWithProducts(ENV_1_NAME, "preview", true,
	//				productsEntryWithWildcard(PRODUCT_1_NAME)));
	//		writer.addEnvironmentDefinition(
	//				environmentDefinitionWithProducts(ENV_2_NAME, "preview", true, productsEntrysFor(product1LastTag)));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//
	//		List<EnvironmentDefinition> resultingVersions = reader.getEnvironmentsWithProductDeployed(product1Preview);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(0));
	//
	//		resultingVersions = reader.getEnvironmentsWithProductDeployed(product1LastTag);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(2));
	//		Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is(ENV_1_NAME));
	//		Assert.assertThat(resultingVersions.get(1).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(1).base.name, is(ENV_2_NAME));
	//	}
	//
	//	@Test
	//	public void shouldIgnoreNonPreviewEnvironmentsWithGivenProduct() throws Exception {
	//		ProductDefinitionProcessor product1 = productDefinitionWithVersion(PRODUCT_1_NAME, "preview", true);
	//		ProductDefinitionProcessor product1_old = productDefinitionWithVersion(PRODUCT_1_NAME, "0.1.0", false);
	//		ProductDefinitionProcessor product2 = productDefinitionWithVersion(PRODUCT_2_NAME, "preview", true);
	//		DataStore.ProductDefinitionReference product1Entry = DataStore.ProductDefinitionReference
	//				.fromProductDefinition(product1);
	//		DataStore.ProductDefinitionReference product1Entry_old = DataStore.ProductDefinitionReference
	//				.fromProductDefinition(product1_old);
	//		DataStore.ProductDefinitionReference product2Entry = DataStore.ProductDefinitionReference
	//				.fromProductDefinition(product2);
	//
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		writer.addEnvironmentDefinition(environmentDefinitionWithProducts(ENV_1_NAME, "preview", true,
	//				productEntrysFor(product1Entry, product2Entry)));
	//		writer.addEnvironmentDefinition(environmentDefinitionWithProducts(ENV_1_NAME, "0.2.0", false,
	//				productEntrysFor(product1Entry_old, product2Entry)));
	//		writer.addEnvironmentDefinition(
	//				environmentDefinitionWithProducts(ENV_2_NAME, "preview", true, productEntrysFor(product1Entry)));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//
	//		List<EnvironmentDefinition> resultingVersions = reader.getEnvironmentsWithProductDeployed(product1);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(2));
	//		Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is(ENV_1_NAME));
	//		Assert.assertThat(resultingVersions.get(1).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(1).base.name, is(ENV_2_NAME));
	//
	//		resultingVersions = reader.getEnvironmentsWithProductDeployed(product2);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(1));
	//		Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is(ENV_1_NAME));
	//
	//		resultingVersions = reader.getEnvironmentsWithProductDeployed(product1_old);
	//		resultingVersions.sort((a, b) -> a.base.name.compareTo(b.base.name));
	//		Assert.assertThat(resultingVersions.size(), is(0));
	//	}
	//
	//	@Test
	//	public void shouldReturnProductsInEnvironmentsWithGivenVersionFilter() throws Exception {
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		ProductDefinitionProcessor product1 = productDefinitionWithVersion("product1", "preview", true);
	//		ProductDefinitionProcessor product1Old = productDefinitionWithVersion("product1", "0.1.0", false);
	//		ProductDefinitionProcessor product2 = productDefinitionWithVersion("product2", "0.2.0", false);
	//		ProductDefinitionProcessor product3 = productDefinitionWithVersion("product3", "0.1.0", false);
	//		writer.addProductDefinition(product1);
	//		writer.addProductDefinition(product1Old);
	//		writer.addProductDefinition(product2);
	//		writer.addProductDefinition(product3);
	//
	//		writer.addEnvironmentDefinition(
	//				environmentDefinitionWithProducts("env1", "preview", true, productsEntrysFor(product1, product2)));
	//		writer.addEnvironmentDefinition(environmentDefinitionWithProducts("env1", "0.3.0", false, asList()));
	//		writer.addEnvironmentDefinition(
	//				environmentDefinitionWithProducts("env2", "preview", true, productsEntrysFor(product3)));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//		List<ProductDefinitionProcessor> resultingVersions = reader.getProductDefinitionsForEnvironment("env1", "preview");
	//		Assert.assertThat(resultingVersions.size(), is(2));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is("product1"));
	//		Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
	//		Assert.assertThat(resultingVersions.get(1).base.name, is("product2"));
	//		Assert.assertThat(resultingVersions.get(1).base.version.getSingleVersionString(), is("0.2.0"));
	//	}
	//
	//	@Test
	//	public void shouldReturnProductsInEnvironmentsWithLatestTag() throws Exception {
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		ProductDefinitionProcessor product1Preview = productDefinitionWithVersion(PRODUCT_1_NAME, "preview", true);
	//		ProductDefinitionProcessor product1LastTag = productDefinitionWithVersion(PRODUCT_1_NAME, "0.2.0", false);
	//		ProductDefinitionProcessor product1Old = productDefinitionWithVersion(PRODUCT_1_NAME, "0.1.0", false);
	//		writer.addProductDefinition(product1Preview);
	//		writer.addProductDefinition(product1LastTag);
	//		writer.addProductDefinition(product1Old);
	//
	//		writer.addEnvironmentDefinition(
	//				environmentDefinitionWithProducts("env1", "preview", true, productsEntryWithWildcard(PRODUCT_1_NAME)));
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//		List<ProductDefinitionProcessor> resultingVersions = reader.getProductDefinitionsForEnvironment("env1", "preview");
	//		Assert.assertThat(resultingVersions.size(), is(1));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is("product1"));
	//		Assert.assertThat(resultingVersions.get(0).base.version.getSingleVersionString(), is("0.2.0"));
	//	}
	//
	//	private List<EnvironmentProductDefinitionReference> productsEntryWithWildcard(String productName) {
	//		return asList(new EnvironmentProductDefinitionReference(productName,
	//				ProjectVersionWithWildcard.fromSingleString("*")));
	//	}
	//
	//	private List<EnvironmentProductDefinitionReference> productsEntrysFor(ProductDefinitionProcessor... products) {
	//		return Arrays.stream(products)
	//				.map(p -> new EnvironmentProductDefinitionReference(p.base.name, fromProjectVersion(p.base.version)))
	//				.collect(Collectors.toList());
	//	}
	//
	//	private List<EnvironmentProductDefinitionReference> productEntrysFor(ProductDefinitionReference... productRefs) {
	//		return Arrays.stream(productRefs)
	//				.map(p -> new EnvironmentProductDefinitionReference(p.name, fromProjectVersion(p.version)))
	//				.collect(Collectors.toList());
	//	}
	//
	//	private ProjectVersionWithWildcard fromProjectVersion(ProjectVersion version) {
	//		return new ProjectVersionWithWildcard(version, false);
	//	}
	//
	//	private EnvironmentDefinition environmentDefinitionWithVersion(String version, boolean isPreview) {
	//		return new EnvironmentDefinition(
	//				new DefinitionBase(DEFAULT_PROJECT_NAME, new ProjectVersion(version, isPreview), null, "arepo", null),
	//				null);
	//	}
	//
	//	private EnvironmentDefinition environmentDefinitionWithProducts(String envName, String version, boolean isPreview,
	//			List<DataStore.EnvironmentProductDefinitionReference> products) {
	//		return new EnvironmentDefinition(
	//				new DefinitionBase(envName, new ProjectVersion(version, isPreview), null, "arepo", null), products);
	//	}
	//
	//	private ProductDefinitionProcessor productDefinitionWithVersion(String productName, String version, boolean isPreview) {
	//		return new ProductDefinitionProcessor(
	//				new DefinitionBase(productName, new ProjectVersion(version, isPreview), null, "arepo", null), null);
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
