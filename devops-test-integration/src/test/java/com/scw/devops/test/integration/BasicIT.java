package com.scw.devops.test.integration;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.scw.devops.test.integration.gitlab.gateway.ResourceFolderGateway;
import com.scw.devops.test.integration.resources.ResourceAccess;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(GitlabGatewayConfiguration.class)
public class BasicIT {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	private ResourceFolderGateway gitlabGateway;

	@Test
	public void runThruIntegrationTest() throws Exception {
		String apiName = "runthru";
		whenDataIngested(ResourceAccess.getGitInputDirectory(apiName));

		assertQuery("test1_environments");
		assertQuery("test1_environments_without_preview");
		// TODO fix
		//		assertQuery("test1_products");
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
		whenDataIngestedThenWebhooksProcessed(ResourceAccess.getGitInputDirectory("runthru"),
				ResourceAccess.getGitInputDirectory("runthru_after_webhook"));

		// TODO fix
		//assertQuery("test1_after_webhook_environments");
		//assertQuery("test1_after_webhook_products");
		//assertQuery("test1_after_webhook_applications");
		//assertQuery("test1_after_webhook_orphaned_applications");
	}

	private void assertQuery(final String apiName) throws Exception {
		String responseJson = post( ResourceAccess.getRestApiRequestUri( apiName ), ResourceAccess.getRestApiRequestBody( apiName ) );

		String expectedJson = ResourceAccess.getRestApiExpectedResponseJson( apiName );
		// Note the order is not important
		JSONAssert.assertEquals(expectedJson, responseJson, JSONCompareMode.LENIENT);
	}

	private void whenDataIngested(final String gitInputDirectory) throws Exception {
		gitlabGateway.setProjectDirectory(gitInputDirectory);
		get("/ingest/all");
	}

	private void whenDataIngestedThenWebhooksProcessed(final String initialGitInputDirectory,
			final String postWebhookGitInputDirectory) throws Exception {
		gitlabGateway.setProjectDirectory(initialGitInputDirectory);
		get("/ingest/all");

		gitlabGateway.setProjectDirectory(postWebhookGitInputDirectory);
		processWebhook("1_test1_push_product3_webhook.json");
		processWebhook("2_test1_tag_product1_webhook.json");
		processWebhook("3_test1_push_env_test1_webhook.json");
		processWebhook("4_test1_tag_app3_webhook.json");
		processWebhook("5_test1_push_app1_webhook.json");
		processWebhook("6_test1_push_app4-not-in-product_webhook.json");
	}

	private void processWebhook(final String webhookBodyFile) throws Exception {
		post("/webhooks", ResourceAccess.getWebhookBody(webhookBodyFile));
	}

	private String post(final String uri, final String body) throws Exception {
		var request = MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_UTF8).content(body);
		return getResponseAsString(request);
	}

	protected String get(final String uri) throws Exception {
		return getResponseAsString(MockMvcRequestBuilders.get(uri));
	}

	private String getResponseAsString(final MockHttpServletRequestBuilder request) throws Exception {
		return this.mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse()
				.getContentAsString();
	}
}
