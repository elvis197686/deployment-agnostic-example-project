package com.scw.devops.test.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.DevopsQueryCommandResult;
import com.scw.devops.deploy.config.ApplicationConfiguration;
import com.scw.devops.test.integration.resources.ResourceAccess;

public class BasicIT {

	private static Application application;
	private static ObjectMapper jsonMapper;

	@BeforeAll
	public static void setUp() {
		application = new Application();
		jsonMapper = new ApplicationConfiguration().objectMapper();
	}

	@Test
	public void runThruIntegrationTest() throws Throwable {
		String apiName = "runthru";
		whenDataIngested(ResourceAccess.getGitInputDirectory(apiName));

		assertQuery("test1_environments");
		assertQuery("test1_environments_without_preview");
		assertQuery( "test1_products" );
		//		assertQuery("test1_products_without_preview");
		//		assertQuery("test1_applications");
		//		assertQuery("test1_applications_without_preview");
		//		assertQuery("test1_applications_all_versions");
		//		assertQuery("test1_applications_specific_version");
		//		assertQuery("test1_orphaned_applications");
		//		assertQuery("test1_products_in_environment");
		//		assertQuery("test1_products_in_application");
		//		assertQuery("test1_previous_application");
		//		assertQuery("test1_products_without_class_set");
	}

	@Test
	public void runThruWebhookTest() throws Exception {
		// TODO fix
		//		whenDataIngestedThenWebhooksProcessed(ResourceAccess.getGitInputDirectory("runthru"),
		//				ResourceAccess.getGitInputDirectory("runthru_after_webhook"));
		//
		//		assertQuery("test1_after_webhook_environments");
		//		assertQuery("test1_after_webhook_products");
		//		assertQuery("test1_after_webhook_applications");
		//		assertQuery("test1_after_webhook_orphaned_applications");
	}

	private void assertQuery(final String apiName) throws Exception {
		DevopsQueryCommand request = parseQueryCommand( ResourceAccess.getQueryCommandJson( apiName ) );
		DevopsQueryCommandResult result = application.doQuery( request );
		String responseJson = formatQueryCommandResult( result );

		String expectedJson = ResourceAccess.getExpectedResponseJson( apiName );
		// Note the order is not important
		JSONAssert.assertEquals(expectedJson, responseJson, JSONCompareMode.LENIENT);
	}

	private String formatQueryCommandResult( final DevopsQueryCommandResult result ) throws Exception {
		String resultString = jsonMapper.writeValueAsString( result );
		System.out.println( "Result: " + resultString );
		return resultString;
	}

	private DevopsQueryCommand parseQueryCommand( final String queryCommandJson ) throws Exception {
		return jsonMapper.readValue( queryCommandJson, DevopsQueryCommand.class );
	}

	private void whenDataIngested( final String gitInputDirectory ) throws Throwable {
		application.ingestAll( gitInputDirectory );
	}

	// TODO
	//	private void whenDataIngestedThenWebhooksProcessed(final String initialGitInputDirectory,
	//			final String postWebhookGitInputDirectory) throws Exception {
	//		gitlabGateway.setProjectDirectory(initialGitInputDirectory);
	//		get("/ingest/all");
	//
	//		gitlabGateway.setProjectDirectory(postWebhookGitInputDirectory);
	//		processWebhook("1_test1_push_product3_webhook.json");
	//		processWebhook("2_test1_tag_product1_webhook.json");
	//		processWebhook("3_test1_push_env_test1_webhook.json");
	//		processWebhook("4_test1_tag_app3_webhook.json");
	//		processWebhook("5_test1_push_app1_webhook.json");
	//		processWebhook("6_test1_push_app4-not-in-product_webhook.json");
	//	}
	//
	//	private void processWebhook(final String webhookBodyFile) throws Exception {
	//		post("/webhooks", ResourceAccess.getWebhookBody(webhookBodyFile));
	//	}
}
