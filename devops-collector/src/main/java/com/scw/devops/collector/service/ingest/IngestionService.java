package com.scw.devops.collector.service.ingest;

import java.util.function.Consumer;

import org.slf4j.Logger;

import com.scw.devops.collector.application.CollectorAutowiring;
import com.scw.devops.collector.domain.ProjectData;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.ProductDefinition;

public class IngestionService {
	private final String environmentGroupName;
	private final String productGroupName;
	private final String gitlabUrl;
	private final Logger logger;

	public IngestionService( final CollectorAutowiring autowiring ) {
		this.environmentGroupName = autowiring.getGitlabConfiguration().getEnvironmentGroupName();
		this.productGroupName = autowiring.getGitlabConfiguration().getProductGroupName();
		this.gitlabUrl = autowiring.getGitlabConnectionConfiguration().getGitlabUrl();
		this.logger = autowiring.getLogger();
	}

	public void addEnvironment( final ProjectData environmentGitlabData, final Consumer<EnvironmentDefinition> environmentConsumer ) {
		String id = environmentGitlabData.getIdentifier();
		logger.info("Saving project data for environment: " + id);
		try {
			EnvironmentDefinition definition = environmentGitlabData.transformToEnvironment(gitlabUrl,
					environmentGroupName);
			environmentConsumer.accept( definition );
			logger.info("Saved project data for environment: " + id);
		} catch (Throwable th) {
			logger.error("Unexpected error saving project data for environment: " + id, th);
			// TODO remove when we get logging working
			th.printStackTrace();
			throw new RuntimeException("Error saving data - see logs for stack trace", th);
		}
	}

	public void addProduct( final ProjectData productGitlabData, final Consumer<ProductDefinition> productConsumer ) {
		String id = productGitlabData.getIdentifier();
		logger.info("Saving project data for product: " + id);
		try {
			ProductDefinition definition = productGitlabData.transformToProduct(gitlabUrl, productGroupName);
			productConsumer.accept( definition );
			logger.info("Saved project data for product: " + id);
		} catch (Throwable th) {
			logger.error("Unexpected error saving project data for product: " + id, th);
			throw new RuntimeException("Error saving data - see logs for stack trace", th);
		}
	}

	public void addApplication( final ProjectData gitlabData, final String groupName, final Consumer<ApplicationDefinition> applicationConsumer ) {
		String id = gitlabData.getIdentifier();
		logger.info("Saving project data for application: " + id);
		try {
			ApplicationDefinition definition = gitlabData.transformToApplication(gitlabUrl, groupName);
			applicationConsumer.accept( definition );
			logger.info("Saved project data for application: " + id);
		} catch (Throwable th) {
			logger.error("Unexpected error saving project data for application: " + id, th);
			throw new RuntimeException("Error saving data - see logs for stack trace", th);
		}
	}

}