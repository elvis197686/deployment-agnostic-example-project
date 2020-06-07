package com.scw.devops.query.service;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;

import com.scw.devops.application.AutowiringProviderImpl;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.domain.projectversion.ProjectVersion;

public class DefinitionQueryTest {

	DefinitionQuery objectUnderTest;

	DataStoreReader reader = mock(DataStoreReader.class);
	ProductResolver productResolver = mock(ProductResolver.class);

	AutowiringProviderImpl queryAutowiring;

	@BeforeEach
	public void setup() {
		queryAutowiring = new AutowiringProviderImpl( reader, productResolver );
		objectUnderTest = new DefinitionQuery( queryAutowiring );
	}
	// TODO
	//
	//	@Test
	//	public void shouldReturnEnvironmentsfilteredByName() {
	//		EnvironmentDefinition envWithDevInName = environmentDefinitionWithName("a_name_with_dev");
	//		EnvironmentDefinition anotherEnvWithDevInName = environmentDefinitionWithName("a_name_with_dev_also");
	//		EnvironmentDefinition envWithoutDevInName = environmentDefinitionWithName("a_name");
	//		List<EnvironmentDefinition> dataStoreResponse = Arrays.asList(envWithDevInName, anotherEnvWithDevInName,
	//				envWithoutDevInName);
	//		Mockito.when(reader.getAllEnvironmentDefinitions(Mockito.any(DataStoreReader.VersionQuery.class)))
	//				.thenReturn(dataStoreResponse);
	//
	//		List<Environment> foundEnvironments = objectUnderTest.getEnvironments(Optional.empty(),
	//				new EnvironmentQueryInputFilter("dev", null, null, null));
	//
	//		Assert.assertThat(foundEnvironments.size(), is(2));
	//		EnvironmentAssertions.assertEnvironmentName(foundEnvironments.get(0), "a_name_with_dev");
	//		EnvironmentAssertions.assertEnvironmentName(foundEnvironments.get(1), "a_name_with_dev_also");
	//	}
	//
	//	@Test
	//	public void shouldReturnEnvironmentsfilteredByAutostart() {
	//		EnvironmentDefinition envWithAutoStart = environmentDefinitionWithAutoStart("env_with_auostart", true);
	//		EnvironmentDefinition envWithoutAutoStart = environmentDefinitionWithAutoStart("env_without_auostart", false);
	//		List<EnvironmentDefinition> dataStoreResponse = Arrays.asList(envWithAutoStart, envWithoutAutoStart);
	//		Mockito.when(reader.getAllEnvironmentDefinitions(Mockito.any(DataStoreReader.VersionQuery.class)))
	//				.thenReturn(dataStoreResponse);
	//
	//		List<Environment> foundEnvironments = objectUnderTest.getEnvironments(Optional.empty(),
	//				new EnvironmentQueryInputFilter(null, null, null, true));
	//
	//		Assert.assertThat(foundEnvironments.size(), is(1));
	//		EnvironmentAssertions.assertEnvironmentName(foundEnvironments.get(0), "env_with_auostart");
	//	}
	//
	//	@Test
	//	public void shouldCallProductResolverForAllProducts() {
	//		List<ProductDefinition> productsToBeResolved = Arrays
	//				.asList(productDefinitionWithName("product_name_with_dev1"));
	//		Mockito.when(reader.getAllProductDefinitions(Mockito.any(DataStoreReader.VersionQuery.class)))
	//				.thenReturn(productsToBeResolved);
	//
	//		Optional<Integer> versionLimit = Optional.empty();
	//		StandardQueryInputFilter queryFilter = new StandardQueryInputFilter("dev", null, null, null, null);
	//		objectUnderTest.getProducts(versionLimit, queryFilter);
	//
	//		verify(productResolver).filterProducts(productsToBeResolved, versionLimit, queryFilter);
	//	}
	//
	//	@Test
	//	public void shouldReturnResolvedProductsForAllProducts() {
	//		List<ProductDefinition> productsToBeResolved = Arrays
	//				.asList(productDefinitionWithName("product_name_with_dev1"));
	//		Mockito.when(reader.getAllProductDefinitions(Mockito.any(DataStoreReader.VersionQuery.class)))
	//				.thenReturn(productsToBeResolved);
	//
	//		List<ProductDef> resolvedProducts = Arrays.asList();
	//		Mockito.when(productResolver.filterProducts(Mockito.any(Collection.class), Mockito.any(Optional.class),
	//				Mockito.any(StandardQueryInputFilter.class))).thenReturn(resolvedProducts);
	//
	//		Optional<Integer> versionLimit = Optional.empty();
	//		StandardQueryInputFilter queryFilter = new StandardQueryInputFilter("dev", null, null, null, null);
	//		List<ProductDef> foundProducts = objectUnderTest.getProducts(versionLimit, queryFilter);
	//
	//		Assert.assertThat(foundProducts, is(resolvedProducts));
	//	}
	//
	//	@Test
	//	public void shouldCallProductResolverForApplicationProducts() {
	//		String applicationName = "anApp";
	//		String applicationVersion = "0.2.0";
	//
	//		List<ProductDefinition> productsToBeResolved = Arrays
	//				.asList(productDefinitionWithName("product_name_with_dev1"));
	//		Mockito.when(reader.getProductDefinitionsForApplication(Mockito.any(String.class), Mockito.any(String.class),
	//				Mockito.any(DataStoreReader.VersionQuery.class))).thenReturn(productsToBeResolved);
	//
	//		Optional<Integer> versionLimit = Optional.empty();
	//		StandardQueryInputFilter queryFilter = new StandardQueryInputFilter("dev", null, null, null, null);
	//		objectUnderTest.getProductsInApplication(applicationName, applicationVersion, versionLimit, queryFilter);
	//
	//		verify(productResolver).filterProducts(productsToBeResolved, versionLimit, queryFilter);
	//	}
	//
	//	@Test
	//	public void shouldReturnResolvedProductsForApplicationProducts() {
	//		List<ProductDefinition> productsToBeResolved = Arrays
	//				.asList(productDefinitionWithName("product_name_with_dev1"));
	//		Mockito.when(reader.getProductDefinitionsForApplication(Mockito.any(String.class), Mockito.any(String.class),
	//				Mockito.any(DataStoreReader.VersionQuery.class))).thenReturn(productsToBeResolved);
	//
	//		List<ProductDef> resolvedProducts = Arrays.asList();
	//		Mockito.when(productResolver.filterProducts(Mockito.any(Collection.class), Mockito.any(Optional.class),
	//				Mockito.any(StandardQueryInputFilter.class))).thenReturn(resolvedProducts);
	//
	//		Optional<Integer> versionLimit = Optional.empty();
	//		StandardQueryInputFilter queryFilter = new StandardQueryInputFilter("dev", null, null, null, null);
	//		List<ProductDef> foundProducts = objectUnderTest.getProducts(versionLimit, queryFilter);
	//
	//		Assert.assertThat(foundProducts, is(resolvedProducts));
	//	}
	//
	//	@Test
	//	public void shouldCallProductResolverForEnvironmentProducts() {
	//		String envName = "anEnv";
	//		String envVersion = "0.2.0";
	//
	//		List<ProductDefinition> productsToBeResolved = Arrays
	//				.asList(productDefinitionWithName("product_name_with_dev1"));
	//		Mockito.when(reader.getProductDefinitionsForEnvironment(Mockito.any(String.class), Mockito.any(String.class)))
	//				.thenReturn(productsToBeResolved);
	//
	//		Optional<Integer> versionLimit = Optional.empty();
	//		Optional<String> classFilter = Optional.of("exampleClass");
	//		objectUnderTest.getProductsInEnvironment(envName, envVersion, classFilter);
	//
	//		ArgumentCaptor<Collection<ProductDefinition>> productsCaptor = ArgumentCaptor.forClass(Collection.class);
	//		ArgumentCaptor<Optional<Integer>> versionLimitCaptor = ArgumentCaptor.forClass(Optional.class);
	//		ArgumentCaptor<StandardQueryInputFilter> versionQueryCaptor = ArgumentCaptor
	//				.forClass(StandardQueryInputFilter.class);
	//		verify(productResolver).filterProducts(productsCaptor.capture(), versionLimitCaptor.capture(),
	//				versionQueryCaptor.capture());
	//
	//		Assert.assertThat(productsCaptor.getValue(), is(productsToBeResolved));
	//		Assert.assertThat(versionLimitCaptor.getValue(), is(versionLimit));
	//		Assert.assertThat(versionQueryCaptor.getValue().getWantedClass().get(), is(classFilter.get()));
	//	}
	//
	//	@Test
	//	public void shouldReturnResolvedProductsForEnvironmentProducts() {
	//		List<ProductDefinition> productsToBeResolved = Arrays
	//				.asList(productDefinitionWithName("product_name_with_dev1"));
	//		Mockito.when(reader.getProductDefinitionsForEnvironment(Mockito.any(String.class), Mockito.any(String.class)))
	//				.thenReturn(productsToBeResolved);
	//
	//		List<ProductDef> resolvedProducts = Arrays.asList();
	//		Mockito.when(productResolver.filterProducts(Mockito.any(Collection.class), Mockito.any(Optional.class),
	//				Mockito.any(StandardQueryInputFilter.class))).thenReturn(resolvedProducts);
	//
	//		Optional<Integer> versionLimit = Optional.empty();
	//		StandardQueryInputFilter queryFilter = new StandardQueryInputFilter("dev", null, null, null, null);
	//		List<ProductDef> foundProducts = objectUnderTest.getProducts(versionLimit, queryFilter);
	//
	//		Assert.assertThat(foundProducts, is(resolvedProducts));
	//	}
	//
	//	@Test
	//	public void shouldReturnApplicationsFilteredByName() {
	//		ApplicationDefinition anAppWithDevInName = applicationDefinitionWithName("app_name_with_dev1");
	//		ApplicationDefinition anotherAppWithDevInName = applicationDefinitionWithName("app_name_with_dev2");
	//		ApplicationDefinition anAppWithoutDevInName = applicationDefinitionWithName("app_name");
	//		Mockito.when(reader.getAllApplicationDefinitions(Mockito.any(DataStoreReader.VersionQuery.class)))
	//				.thenReturn(Arrays.asList(anAppWithDevInName, anotherAppWithDevInName, anAppWithoutDevInName));
	//
	//		List<ApplicationDef> foundApplications = objectUnderTest.getApplications(Optional.empty(),
	//				new StandardQueryInputFilter("dev", null, null, null, null));
	//
	//		Assert.assertThat(foundApplications.size(), is(2));
	//		ApplicationAssertions.assertApplicationName(foundApplications.get(0), "app_name_with_dev1");
	//		ApplicationAssertions.assertApplicationName(foundApplications.get(1), "app_name_with_dev2");
	//	}
	//
	//	@Test
	//	public void shouldReturnApplicationsFilteredByValidity() {
	//		ApplicationDefinition anAppWithErrors = applicationDefinitionWithErrors("app_with_errors");
	//		ApplicationDefinition anAppWithoutErrors = applicationDefinitionWithName("app_without_errors");
	//		Mockito.when(reader.getAllApplicationDefinitions(Mockito.any(DataStoreReader.VersionQuery.class)))
	//				.thenReturn(Arrays.asList(anAppWithErrors, anAppWithoutErrors));
	//
	//		List<ApplicationDef> foundApplications = objectUnderTest.getApplications(Optional.empty(),
	//				new StandardQueryInputFilter(null, null, null, "INVALID", null));
	//
	//		Assert.assertThat(foundApplications.size(), is(1));
	//		ApplicationAssertions.assertApplicationName(foundApplications.get(0), "app_with_errors");
	//	}
	//
	//	@Test
	//	public void shouldReturnApplicationsOrderedByNameThenVersion() {
	//		ApplicationDefinition app1Dev = applicationDefinitionWithNameAndVersion("app1", "preview", true);
	//		ApplicationDefinition app1Old = applicationDefinitionWithNameAndVersion("app1", "0.1.0", false);
	//		ApplicationDefinition app2Dev = applicationDefinitionWithNameAndVersion("zzapp2", "preview", true);
	//		Mockito.when(reader.getAllApplicationDefinitions(Mockito.any(DataStoreReader.VersionQuery.class)))
	//				.thenReturn(Arrays.asList(app1Old, app2Dev, app1Dev));
	//
	//		List<ApplicationDef> foundApplications = objectUnderTest.getApplications(Optional.empty(),
	//				new StandardQueryInputFilter(null, null, null, null, null));
	//
	//		Assert.assertThat(foundApplications.size(), is(3));
	//		ApplicationAssertions.assertApplicationNameAndVersion(foundApplications.get(0), "app1", "preview");
	//		ApplicationAssertions.assertApplicationNameAndVersion(foundApplications.get(1), "app1", "0.1.0");
	//		ApplicationAssertions.assertApplicationName(foundApplications.get(2), "zzapp2");
	//	}

	private EnvironmentDefinition environmentDefinitionWithName(final String envName) {
		return new EnvironmentDefinition(
										  new DefinitionBase( envName, ProjectVersion.previewVersion(), null, "arepo", null ),
										  null );
	}

	private EnvironmentDefinition environmentDefinitionWithAutoStart(final String name, final boolean autoStart) {
		// TODO
		return null;
		//return new EnvironmentDefinition(new DefinitionBase(name, new ProjectVersion("preview", true),
		//		EnvironmentDefinitionTestData.mapWithAutoStart(autoStart), "arepo", null), null);
	}

	private ProductDefinition productDefinitionWithName(final String name) {
		return new ProductDefinition( new DefinitionBase( name,
														  ProjectVersion.previewVersion(),
														  null,
														  "arepo",
														  null ),
				null);
	}

	private ApplicationDefinition applicationDefinitionWithNameAndVersion(final String name, final String version,
			final boolean isPreview) {
		return new ApplicationDefinition(
										  new DefinitionBase( name,
															  isPreview ? ProjectVersion.previewVersion() : ProjectVersion.namedVersion( version ),
															  null,
															  "arepo",
															  null ) );
	}

	private ApplicationDefinition applicationDefinitionWithName(final String name) {
		return new ApplicationDefinition(
										  new DefinitionBase( name, ProjectVersion.previewVersion(), null, "arepo", null ) );
	}

	private ApplicationDefinition applicationDefinitionWithErrors(final String name) {
		// TODO
		return null;
		//return new ApplicationDefinition(new DefinitionBase(name, new ProjectVersion("previww", true), null, "arepo",
		//	Arrays.asList(new ConfigurationErrorData("error"))));
	}
}
