package com.scw.devops.store;

import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.is;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.scw.devops.application.AutowiringProviderImpl;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.ApplicationDefinitionAssertions;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntry;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.common.data.ProductDefinitionProcessor;
import com.scw.devops.contract.store.query.data.VersionQuery;
import com.scw.devops.domain.projectversion.ProjectVersion;
import com.scw.devops.store.application.StoreAutowiring;
import com.scw.devops.store.service.DataStoreQueryService;
import com.scw.devops.store.service.DataStoreUpdateService;
import com.scw.devops.store.state.DataStore;

public class DataStoreApplicationTest {

	private static final String APP_1_REPO_NAME = "app1_src";
	private static final String APP_1_ALIAS_1 = "app1Alias1";
	private static final String APP_1_ALIAS_2 = "app1Alias2";

	private static final String PRODUCT_1_NAME = "product1";
	private static final String DEFAULT_APPLICATION_NAME = "app1";

	private static StoreAutowiring autowirer = null;

	@BeforeEach
	public void init() {
		autowirer = new AutowiringProviderImpl();
	}

	@Test
	public void shouldPutPreviewAtStart() throws Exception {
		DataStore store = new DataStore();
		DataStoreUpdateService writer = new DataStoreUpdateService( autowirer );
		writer.addApplicationDefinition(applicationDefinitionWithVersion("0.3.0", false));
		writer.addApplicationDefinition(applicationDefinitionWithVersion("0.1.0", false));
		writer.addApplicationDefinition(applicationDefinitionWithVersion("preview", true));
		writer.addApplicationDefinition(applicationDefinitionWithVersion("0.4.0", false));

		DataStoreQueryService reader = new DataStoreQueryService( autowirer );
		List<ApplicationDefinition> resultingVersions = reader
				.getAllApplicationDefinitions(versionQueryWithPreview(true));
		ApplicationDefinitionAssertions.assertVersions( resultingVersions );
		// TODO
		//Assert.assertThat(resultingVersions.size(), is(4));
		//Assert.assertThat(resultingVersions.get(0).base.version.isPreview(), is(true));
		//Assert.assertThat(resultingVersions.get(1).base.version.getSingleVersionString(), is("0.4.0"));
		//Assert.assertThat(resultingVersions.get(2).base.version.getSingleVersionString(), is("0.3.0"));
		//Assert.assertThat(resultingVersions.get(3).base.version.getSingleVersionString(), is("0.1.0"));
	}

	@Test
	public void shouldPutInvalidVersionsAtEnd() throws Exception {
		DataStoreUpdateService writer = new DataStoreUpdateService( autowirer );
		writer.addApplicationDefinition(applicationDefinitionWithVersion("0.3.0", false));
		writer.addApplicationDefinition(applicationDefinitionWithVersion("0.1.0", false));
		writer.addApplicationDefinition(applicationDefinitionWithVersion("badVersion0.2.0", false));
		writer.addApplicationDefinition(applicationDefinitionWithVersion("0.4.0", false));

		DataStoreQueryService reader = new DataStoreQueryService( autowirer );
		List<ApplicationDefinition> resultingVersions = reader
				.getAllApplicationDefinitions(versionQueryWithPreview(true));
		Assert.assertThat(resultingVersions.size(), is(4));
		// TODO
		//Assert.assertThat(resultingVersions.get(0).base.version.getSingleVersionString(), is("0.4.0"));
		//Assert.assertThat(resultingVersions.get(1).base.version.getSingleVersionString(), is("0.3.0"));
		//Assert.assertThat(resultingVersions.get(2).base.version.getSingleVersionString(), is("0.1.0"));
		//		Assert.assertThat(resultingVersions.get(3).base.version.getSingleVersionString(), is("badVersion0.2.0"));
	}

	@Test
	public void shouldGetHighestReleaseVersionForPreviewVersion() throws Exception {
		ApplicationDefinition previewDefinition = applicationDefinitionWithVersion("develop", true);
		ApplicationDefinition highestReleaseDefinition = applicationDefinitionWithVersion("0.2.0", false);
		ApplicationDefinition olderReleaseDefinition = applicationDefinitionWithVersion("0.1.0", false);

		DataStoreUpdateService writer = new DataStoreUpdateService( autowirer );
		writer.addApplicationDefinition(previewDefinition);
		writer.addApplicationDefinition(highestReleaseDefinition);
		writer.addApplicationDefinition(olderReleaseDefinition);

		DataStoreQueryService reader = new DataStoreQueryService( autowirer );
		Optional<ApplicationDefinition> foundDefinition = reader.getPreviousApplicationDefinition(previewDefinition);

		Assert.assertTrue(foundDefinition.isPresent());
		// TODO
		//Assert.assertThat(foundDefinition.get().base.name, is(highestReleaseDefinition.base.name));
	}

	@Test
	public void shouldGetNextOldestReleaseVersionForReleaseVersion() throws Exception {
		ApplicationDefinition previewDefinition = applicationDefinitionWithVersion("develop", true);
		ApplicationDefinition highestReleaseDefinition = applicationDefinitionWithVersion("0.2.0", false);
		ApplicationDefinition olderReleaseDefinition = applicationDefinitionWithVersion("0.1.0", false);

		DataStoreUpdateService writer = new DataStoreUpdateService( autowirer );
		writer.addApplicationDefinition(previewDefinition);
		writer.addApplicationDefinition(highestReleaseDefinition);
		writer.addApplicationDefinition(olderReleaseDefinition);

		DataStoreQueryService reader = new DataStoreQueryService( autowirer );
		Optional<ApplicationDefinition> foundDefinition = reader
				.getPreviousApplicationDefinition(highestReleaseDefinition);

		Assert.assertTrue(foundDefinition.isPresent());
		// TODO
		//Assert.assertThat(foundDefinition.get().base.name, is(olderReleaseDefinition.base.name));
	}

	@Test
	public void shouldNotGetPreviousVersionForOldestReleaseVersion() throws Exception {
		ApplicationDefinition previewDefinition = applicationDefinitionWithVersion("develop", true);
		ApplicationDefinition highestReleaseDefinition = applicationDefinitionWithVersion("0.2.0", false);
		ApplicationDefinition olderReleaseDefinition = applicationDefinitionWithVersion("0.1.0", false);

		DataStoreUpdateService writer = new DataStoreUpdateService( autowirer );
		writer.addApplicationDefinition(previewDefinition);
		writer.addApplicationDefinition(highestReleaseDefinition);
		writer.addApplicationDefinition(olderReleaseDefinition);

		DataStoreQueryService reader = new DataStoreQueryService( autowirer );
		Optional<ApplicationDefinition> foundDefinition = reader
				.getPreviousApplicationDefinition(olderReleaseDefinition);

		Assert.assertFalse(foundDefinition.isPresent());
	}

	// TODO
	//	@Test
	//	public void shouldReturnApplicationsWithGivenProduct() throws Exception {
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//
	//		ApplicationInstanceEntry app1Alias1 = new ApplicationInstanceEntry(APP_1_ALIAS_1, APP_1_REPO_NAME,
	//				new ProjectVersion("develop", true));
	//		writer.addApplicationDefinition(applicationDefinitionWithNameAndVersion(APP_1_REPO_NAME, "develop", true));
	//		ApplicationInstanceEntry app1Alias2 = new ApplicationInstanceEntry(APP_1_ALIAS_2, APP_1_REPO_NAME,
	//				new ProjectVersion("0.3.0", false));
	//		writer.addApplicationDefinition(applicationDefinitionWithNameAndVersion(APP_1_REPO_NAME, "0.3.0", false));
	//
	//		ProductDefinitionProcessor product1 = productDefinitionWithVersionAndApplications(PRODUCT_1_NAME, "preview", true,
	//				asList(app1Alias1, app1Alias2));
	//
	//		writer.addProductDefinition(product1);
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//
	//		List<StoreApplicationInstance> resultingVersions = reader.getProductApplicationReferences(product1);
	//		Assert.assertThat(resultingVersions.size(), is(2));
	//		Assert.assertThat(resultingVersions.get(0).alias, is(APP_1_ALIAS_1));
	//		Assert.assertThat(resultingVersions.get(0).applicationDefinition.base.name, is(APP_1_REPO_NAME));
	//		Assert.assertThat(resultingVersions.get(1).alias, is(APP_1_ALIAS_2));
	//		Assert.assertThat(resultingVersions.get(1).applicationDefinition.base.name, is(APP_1_REPO_NAME));
	//	}
	//
	//	@Test
	//	public void shouldReturnProductsInApplicationWithGivenVersionFilter() throws Exception {
	//		// Note: We only test includePreview as a filter because we cannot see any need
	//		// for such filtering.. we can add filters as/when we see the need for them
	//		DataStore store = new DataStore();
	//		DataStoreUpdateService writer = new DataStoreUpdateService(store);
	//		ApplicationDefinition app1 = applicationDefinitionWithNameAndVersion("app1", "preview", true);
	//		ApplicationDefinition app2 = applicationDefinitionWithNameAndVersion("app1", "0.3.0", false);
	//		ApplicationDefinition app3 = applicationDefinitionWithNameAndVersion("app2", "preview", true);
	//		writer.addApplicationDefinition(app1);
	//		writer.addApplicationDefinition(app2);
	//		writer.addApplicationDefinition(app3);
	//
	//		ProductDefinitionProcessor product1 = productDefinitionWithVersionAndApplications("product1", "preview", true,
	//				applicationEntriesFor(app1, app2));
	//		ProductDefinitionProcessor product1Old = productDefinitionWithVersionAndApplications("product1", "0.1.0", false,
	//				applicationEntriesFor(app1));
	//		ProductDefinitionProcessor product2 = productDefinitionWithVersionAndApplications("product2", "0.2.0", false,
	//				applicationEntriesFor(app2, app3));
	//		ProductDefinitionProcessor product3 = productDefinitionWithVersionAndApplications("product3", "0.1.0", false, asList());
	//		ProductDefinitionProcessor product4 = productDefinitionWithVersionAndApplications("product4", "preview", true,
	//				applicationEntriesFor(app1));
	//		writer.addProductDefinition(product1);
	//		writer.addProductDefinition(product1Old);
	//		writer.addProductDefinition(product2);
	//		writer.addProductDefinition(product3);
	//		writer.addProductDefinition(product4);
	//
	//		DataStoreQueryService reader = new DataStoreQueryService(store);
	//		List<ProductDefinitionProcessor> resultingVersions = reader.getProductDefinitionsForApplication("app1", "0.3.0",
	//				versionQueryWithPreview(false));
	//		Assert.assertThat(resultingVersions.size(), is(2));
	//		Assert.assertThat(resultingVersions.get(0).base.name, is("product2"));
	//		Assert.assertThat(resultingVersions.get(0).base.version.getSingleVersionString(), is("0.2.0"));
	//		Assert.assertThat(resultingVersions.get(1).base.name, is("product1"));
	//		Assert.assertThat(resultingVersions.get(1).base.version.getSingleVersionString(), is("preview"));
	//	}

	private Collection<ApplicationInstanceEntry> applicationEntriesFor(final ApplicationDefinition... apps) {
		// TODO
		return null;
		//return Arrays.stream(apps).map(a -> new ApplicationInstanceEntry("anAlias", a.base.name, a.base.version))
		//	.collect(Collectors.toList());
	}

	private ApplicationDefinition applicationDefinitionWithVersion(final String version, final boolean isPreview) {
		return new ApplicationDefinition(new DefinitionBase(DEFAULT_APPLICATION_NAME,
															  isPreview ? ProjectVersion.previewVersion() : ProjectVersion.namedVersion( version ),
															  null,
															  "arepo",
															  null ) );
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

	private ProductDefinitionProcessor productDefinitionWithVersionAndApplications(final String productName, final String version,
			final boolean isPreview, final Collection<ApplicationInstanceEntry> applications) {
		// TODO
		return null;
		//		return new ProductDefinitionProcessor(
		//			new DefinitionBase(productName, new ProjectVersion(version, isPreview), null, "arepo", null),
		//		applications);
	}

	private VersionQuery versionQueryWithPreview(final boolean includePreview) {
		VersionQuery query = new VersionQuery();
		query.includePreview = includePreview;
		query.versionLimit = 5;
		query.wantedVersionIsPreview = false;
		query.wantedVersionAsSemVer = empty();
		return query;
	}
}
