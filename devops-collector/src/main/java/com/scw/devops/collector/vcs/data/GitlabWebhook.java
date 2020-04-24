package com.scw.devops.collector.vcs.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.ToString;

@ToString
public class GitlabWebhook {

	private static final String OBJECT_KIND_PROPERTY_NAME = "object_kind";
	private static final String REF_PROPERTY_NAME = "ref";
	private static final String PROJECT_PROPERTY_NAME = "project";
	private static final String PROJECT_PATH_WITH_NAMESPACE_PROPERTY_NAME = "path_with_namespace";
	private static final String COMMITS_PROPERTY_NAME = "commits";
	private static final String COMMITS_ADDED_PROPERTY_NAME = "added";
	private static final String COMMITS_MODIFIED_PROPERTY_NAME = "modified";

	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonAnySetter
	public void setAdditionalProperties(String key, Object value) {
		additionalProperties.put(key, value);
	}

	public String getObjectKind() {
		return (String) Optional.ofNullable(additionalProperties.get(OBJECT_KIND_PROPERTY_NAME)).orElse("");
	}

	public String getProjectName() {
		return getProjectNamespacePairEntry(1);
	}

	public String getBranch() {
		String fullBranchRef = (String) Optional.ofNullable(additionalProperties.get(REF_PROPERTY_NAME)).orElse("/");
		return fullBranchRef.substring(fullBranchRef.lastIndexOf('/') + 1);
	}

	public String getGroupName() {
		return getProjectNamespacePairEntry(0);
	}

	@SuppressWarnings("unchecked")
	public Collection<String> getChangedFiles() {
		Collection<Map<String, Object>> commits = (Collection<Map<String, Object>>) additionalProperties
				.get(COMMITS_PROPERTY_NAME);
		return commits.stream().flatMap(c -> getChangedFileList(c)).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private Stream<String> getChangedFileList(Map<String, Object> webhookCommitProperties) {
		List<String> commitFiles = (List<String>) webhookCommitProperties.get(COMMITS_MODIFIED_PROPERTY_NAME);
		commitFiles.addAll((List<String>) webhookCommitProperties.get(COMMITS_ADDED_PROPERTY_NAME));
		return commitFiles.stream();
	}

	private String getProjectNamespacePairEntry(int index) {
		@SuppressWarnings("unchecked")
		Map<String, Object> projectProperties = (Map<String, Object>) additionalProperties.get(PROJECT_PROPERTY_NAME);
		if (projectProperties != null) {
			return ((String) projectProperties.getOrDefault(PROJECT_PATH_WITH_NAMESPACE_PROPERTY_NAME, "/"))
					.split("/")[index];
		}
		return "";
	}

}
