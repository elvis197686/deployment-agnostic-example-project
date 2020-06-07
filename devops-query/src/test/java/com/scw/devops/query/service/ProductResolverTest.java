package com.scw.devops.query.service;

import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.scw.devops.application.AutowiringProviderImpl;
import com.scw.devops.contract.query.data.ProductAssertions;
import com.scw.devops.contract.query.data.ProductDef;
import com.scw.devops.contract.query.data.StandardQueryInputFilter;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.query.DataStoreReader;
import com.scw.devops.contract.store.query.command.GetEnvironmentsWithProductDeployedCommand;
import com.scw.devops.contract.store.query.command.GetProductApplicationReferencesCommand;
import com.scw.devops.contract.store.query.command.OutputApplicationInstances;
import com.scw.devops.contract.store.query.command.OutputEnvironmentDefinitions;
import com.scw.devops.contract.store.query.data.ApplicationInstance;
import com.scw.devops.domain.projectversion.ProjectVersion;

public class ProductResolverTest {

	ProductResolver objectUnderTest;

	DataStoreReader reader = mock(DataStoreReader.class);

	AutowiringProviderImpl queryAutowiring;

	@BeforeEach
	public void setup() {
		queryAutowiring = new AutowiringProviderImpl( reader, null );
		objectUnderTest = new ProductResolver( queryAutowiring );
	}

	@Test
	public void shouldReturnProductsFilteredByName() {
		ProductDefinition aProductWithDevInName = productDefinitionWithName( "product_name_with_dev1" );
		ProductDefinition anotherProductWithDevInName = productDefinitionWithName( "product_name_with_dev2" );
		ProductDefinition aProductWithoutDevInName = productDefinitionWithName( "product_name" );
		List<ProductDefinition> productsToResolve = Arrays.asList( aProductWithDevInName, anotherProductWithDevInName, aProductWithoutDevInName );

		OutputEnvironmentDefinitions dataStoreResponse = new OutputEnvironmentDefinitions( Arrays.asList() );
		Mockito.when( reader.doCommand( Mockito.any( GetEnvironmentsWithProductDeployedCommand.class ) ) ).thenReturn( dataStoreResponse );

		OutputApplicationInstances dataStoreResponseApp = new OutputApplicationInstances( Arrays.asList() );
		Mockito.when( reader.doCommand( Mockito.any( GetProductApplicationReferencesCommand.class ) ) ).thenReturn( dataStoreResponseApp );

		List<ProductDef> foundProducts = objectUnderTest
			.filterProducts( productsToResolve,
							 Optional.empty(),
							 new StandardQueryInputFilter( Optional
								 .of( "dev" ), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty() ) );

		Assert.assertThat( foundProducts.size(), CoreMatchers.is( 2 ) );
		ProductAssertions.assertProductName( foundProducts.get( 0 ), "product_name_with_dev1" );
		ProductAssertions.assertProductName( foundProducts.get( 1 ), "product_name_with_dev2" );
	}

	// TODO
	//	@Test
	//	public void shouldReturnProductsFilteredByValidity() {
	//		ProductDefinition aProductWithErrors = productDefinitionWithErrors("product_with_errors");
	//		ProductDefinition aProductWithoutErrors = productDefinitionWithName("product_without_errors");
	//		List<ProductDefinition> productsToResolve = Arrays.asList(aProductWithErrors, aProductWithoutErrors);
	//
	//		// TOD - Enum for validity
	//		List<ProductDef> foundProducts = objectUnderTest.filterProducts(productsToResolve, Optional.empty(),
	//				new StandardQueryInputFilter(null, null, null, "INVALID", null));
	//
	//		Assert.assertThat(foundProducts.size(), is(1));
	//		ProductAssertions.assertProductName(foundProducts.get(0), "product_with_errors");
	//	}
	//
	//	@Test
	//	public void shouldReturnProductsFilteredByClass() {
	//		ProductDefinition aProductWithTestClass = productDefinitionWithClass("product_with_class_1", "testclass");
	//		ProductDefinition aProductWithInfrastructureClass = productDefinitionWithClass("product_with_class_2",
	//				"infrastructure");
	//		ProductDefinition aProductWithNoClass = productDefinitionWithName("product_without_class");
	//		List<ProductDefinition> productsToResolve = Arrays.asList(aProductWithTestClass,
	//				aProductWithInfrastructureClass, aProductWithNoClass);
	//
	//		List<ProductDef> foundProducts = objectUnderTest.filterProducts(productsToResolve, Optional.empty(),
	//				new StandardQueryInputFilter(null, null, null, null, "infrastructure"));
	//
	//		Assert.assertThat(foundProducts.size(), is(1));
	//		ProductAssertions.assertProductName(foundProducts.get(0), "product_with_class_2");
	//	}
	//
	//	@Test
	//	public void shouldReturnProductsOrderedByNameThenVersion() {
	//		ProductDefinition product1Dev = productDefinitionWithNameAndVersion("product1", "preview", true);
	//		ProductDefinition product1Old = productDefinitionWithNameAndVersion("product1", "0.1.0", false);
	//		ProductDefinition product2Dev = productDefinitionWithNameAndVersion("zzproduct2", "preview", true);
	//		List<ProductDefinition> productsToResolve = Arrays.asList(product1Old, product2Dev, product1Dev);
	//
	//		List<ProductDef> foundProducts = objectUnderTest.filterProducts(productsToResolve, Optional.empty(),
	//				new StandardQueryInputFilter(null, null, null, null, null));
	//
	//		Assert.assertThat(foundProducts.size(), is(3));
	//		ProductAssertions.assertProductNameAndVersion(foundProducts.get(0), "product1", "preview");
	//		ProductAssertions.assertProductNameAndVersion(foundProducts.get(1), "product1", "0.1.0");
	//		ProductAssertions.assertProductName(foundProducts.get(2), "zzproduct2");
	//	}
	//
	//		@Test
	//		public void shouldRequestDeployedToEnvironmentsForEachProduct() {
	//			ProductDefinition aProduct = productDefinitionWithName("aProduct");
	//
	//			objectUnderTest.filterProducts(Arrays.asList(aProduct), Optional.empty(),
	//					new StandardQueryInputFilter("aProduct", null, null, null, null));
	//
	//			ArgumentCaptor<ProductDefinition> productCaptor = ArgumentCaptor.forClass(ProductDefinition.class);
	//			verify(reader).getEnvironmentsWithProductDeployed(productCaptor.capture());
	//
	//			Assert.assertThat(productCaptor.getAllValues().size(), is(1));
	//			Assert.assertThat(productCaptor.getAllValues().get(0), is(aProduct));
	//		}
	//
	//		@Test
	//		public void shouldReturnProductsWithEnvironmentsOrderedByNameThenVersion() {
	//			ProductDefinition aProduct = productDefinitionWithNameAndVersion("aProduct", "preview", true);
	//
	//			EnvironmentDefinition envWithTopName = environmentDefinitionWithVersion("atopname", "0.2.0");
	//			EnvironmentDefinition envWithLowerNameAndTopVersion = environmentDefinitionWithVersion("bothername", "0.2.0");
	//			EnvironmentDefinition envWithLowerNameAndLowerVersion = environmentDefinitionWithVersion("bothername", "0.1.0");
	//			List<EnvironmentDefinition> dataStoreResponse = Arrays.asList(envWithLowerNameAndLowerVersion, envWithTopName,
	//					envWithLowerNameAndTopVersion);
	//			Mockito.when(reader.getEnvironmentsWithProductDeployed(Mockito.any(ProductDefinition.class)))
	//					.thenReturn(dataStoreResponse);
	//
	//			List<ProductDef> foundProducts = objectUnderTest.filterProducts(Arrays.asList(aProduct), Optional.empty(),
	//					new StandardQueryInputFilter("aProduct", null, null, null, null));
	//
	//			Assert.assertThat(foundProducts.size(), is(1));
	//			ProductAssertions.assertProductHasDeployedEnvironmentsInOrder(foundProducts.get(0),
	//					Arrays.asList(new ProductAssertions.NameAndVersion("atopname", "0.2.0"),
	//							new ProductAssertions.NameAndVersion("bothername", "0.2.0"),
	//							new ProductAssertions.NameAndVersion("bothername", "0.1.0")));
	//		}
	//
	//		@Test
	//		public void shouldRequestApplicationsForEachProduct() {
	//			ProductDefinition aProduct = productDefinitionWithName("aProduct");
	//			Mockito.when(reader.getAllProductDefinitions(Mockito.any(DataStoreReader.VersionQuery.class)))
	//					.thenReturn(Arrays.asList(aProduct));
	//
	//			objectUnderTest.filterProducts(Arrays.asList(aProduct), Optional.empty(),
	//					new StandardQueryInputFilter("aProduct", null, null, null, null));
	//
	//			ArgumentCaptor<ProductDefinition> productCaptor = ArgumentCaptor.forClass(ProductDefinition.class);
	//			verify(reader).getProductApplicationReferences(productCaptor.capture());
	//
	//			Assert.assertThat(productCaptor.getAllValues().size(), is(1));
	//			Assert.assertThat(productCaptor.getAllValues().get(0), is(aProduct));
	//		}
	//
	//		@Test
	//		public void shouldReturnProductsWithApplicationAliases() {
	//			ProductDefinition aProduct = productDefinitionWithNameAndVersion("aProduct", "preview", true);
	//
	//			ApplicationInstance appInstance1 = applicationInstance("app1Alias1", "app1");
	//			ApplicationInstance appInstance2 = applicationInstance("app1Alias2", "app1");
	//			ApplicationInstance appInstance3 = applicationInstance("app2Alias", "app2");
	//			List<ApplicationInstance> dataStoreResponse = Arrays.asList(appInstance1, appInstance2, appInstance3);
	//			Mockito.when(reader.getProductApplicationReferences(Mockito.any(ProductDefinition.class)))
	//					.thenReturn(dataStoreResponse);
	//
	//			List<ProductDef> foundProducts = objectUnderTest.filterProducts(Arrays.asList(aProduct), Optional.empty(),
	//					new StandardQueryInputFilter("aProduct", null, null, null, null));
	//
	//			Assert.assertThat(foundProducts.size(), is(1));
	//			ProductAssertions.assertProductHasApplications(foundProducts.get(0),
	//					Arrays.asList(new ProductAssertions.AliasAndName("app1Alias1", "app1"),
	//							new ProductAssertions.AliasAndName("app1Alias2", "app1"),
	//							new ProductAssertions.AliasAndName("app2Alias", "app2")));
	//		}

	private ApplicationInstance applicationInstance(final String alias, final String name) {
		return new ApplicationInstance(alias, new ApplicationDefinition(
																		  new DefinitionBase( name,
																							  ProjectVersion.previewVersion(),
																							  null,
																							  "arepo",
																							  null ) ) );
	}

	private EnvironmentDefinition environmentDefinitionWithVersion(final String envName, final String version) {
		return new EnvironmentDefinition(
										  new DefinitionBase( envName, ProjectVersion.namedVersion( version ), null, "arepo", null ),
										  null );
	}

	private ProductDefinition productDefinitionWithNameAndVersion(final String name, final String version, final boolean isPreview) {
		return new ProductDefinition(
									  new DefinitionBase( name,
														  isPreview ? ProjectVersion.previewVersion()
																	: ProjectVersion.namedVersion( version ),
														  null,
														  "arepo",
														  null ),
									  null );
	}

	private ProductDefinition productDefinitionWithName(final String name) {
		return new ProductDefinition( new DefinitionBase( name, ProjectVersion.previewVersion(), null, "arepo", null ),
				null);
	}

	private ProductDefinition productDefinitionWithErrors(final String name) {
		// TODO
		return null;
		//return new ProductDefinition(new DefinitionBase(name, new ProjectVersion("previww", true), null, "arepo",
		//	Arrays.asList(new ConfigurationErrorData("error"))), null);
	}

	private ProductDefinition productDefinitionWithClass(final String name, final String className) {
		// TODO
		return null;
		//return new ProductDefinition(new DefinitionBase(name, new ProjectVersion("previww", true),
		//	ProductDefinitionTestData.mapWithClass(className), "arepo", null), null);
	}
}
