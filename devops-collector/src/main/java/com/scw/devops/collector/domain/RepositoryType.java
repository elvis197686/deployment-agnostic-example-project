package com.scw.devops.collector.domain;

import java.util.function.Consumer;

public enum RepositoryType {

	APPLICATION, PRODUCT, ENVIRONMENT;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";

	public boolean hasProductFiles() {
		return this == PRODUCT;
	}

	public boolean isEnvironmentRepository() {
		return this == ENVIRONMENT;
	}

	public void processType(Consumer<Void> environmentConsumer, Consumer<Void> productConsumer,
			Consumer<Void> applicationConsumer) {
		switch (this) {
		case ENVIRONMENT:
			environmentConsumer.accept(null);
			break;
		case PRODUCT:
			productConsumer.accept(null);
			break;
		case APPLICATION:
			applicationConsumer.accept(null);
			break;
		}
	}

	public String getPreviewBranch() {
		String defaultBranch = DEVELOP_BRANCH;
		if (this == ENVIRONMENT) {
			defaultBranch = MASTER_BRANCH;
		}
		return defaultBranch;
	}

	public boolean isPreviewBranch(String branchName) {
		return branchName.equals(getPreviewBranch());
	}

	public boolean isApplication() {
		return this == APPLICATION;
	}
}
