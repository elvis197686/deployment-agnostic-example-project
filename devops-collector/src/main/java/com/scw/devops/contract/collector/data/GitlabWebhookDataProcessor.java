package com.scw.devops.contract.collector.data;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GitlabWebhookDataProcessor {

	private static final String OBJECT_KIND_PROPERTY_NAME = "object_kind";
	private static final String REF_PROPERTY_NAME = "ref";
	private static final String PROJECT_PROPERTY_NAME = "project";
	private static final String PROJECT_PATH_WITH_NAMESPACE_PROPERTY_NAME = "path_with_namespace";
	private static final String COMMITS_PROPERTY_NAME = "commits";
	private static final String COMMITS_ADDED_PROPERTY_NAME = "added";
	private static final String COMMITS_MODIFIED_PROPERTY_NAME = "modified";

	public static String getGroupName( final GitlabWebhookData webhook ) {
		return getProjectNamespacePairEntry( webhook, 0 );
	}

	public static String getProjectName( final GitlabWebhookData webhook ) {
		return getProjectNamespacePairEntry( webhook, 1 );
	}

	public static String getBranch( final GitlabWebhookData webhook ) {
		String fullBranchRef = (String) Optional.ofNullable( webhook.additionalProperties.get( REF_PROPERTY_NAME ) ).orElse( "/" );
		return fullBranchRef.substring( fullBranchRef.lastIndexOf( '/' ) + 1 );
	}

	public static String getObjectKind( final GitlabWebhookData webhook ) {
		return (String) Optional.ofNullable( webhook.additionalProperties.get( OBJECT_KIND_PROPERTY_NAME ) ).orElse( "" );
	}

	@SuppressWarnings( "unchecked" )
	public static Collection<String> getChangedFiles( final GitlabWebhookData webhook ) {
		Collection<Map<String, Object>> commits = (Collection<Map<String, Object>>) webhook.additionalProperties.get( COMMITS_PROPERTY_NAME );
		return commits.stream().flatMap( c -> getChangedFileList( c ) ).collect( Collectors.toList() );
	}

	@SuppressWarnings( "unchecked" )
	private static Stream<String> getChangedFileList( final Map<String, Object> webhookCommitProperties ) {
		List<String> commitFiles = (List<String>) webhookCommitProperties.get( COMMITS_MODIFIED_PROPERTY_NAME );
		commitFiles.addAll( (List<String>) webhookCommitProperties.get( COMMITS_ADDED_PROPERTY_NAME ) );
		return commitFiles.stream();
	}

	@SuppressWarnings( "unchecked" )
	private static String getProjectNamespacePairEntry( final GitlabWebhookData webhook, final int index ) {
		Map<String, Object> projectProperties = (Map<String, Object>) webhook.additionalProperties.get( PROJECT_PROPERTY_NAME );
		if ( projectProperties != null ) {
			return ( (String) projectProperties.getOrDefault( PROJECT_PATH_WITH_NAMESPACE_PROPERTY_NAME, "/" ) ).split( "/" )[index];
		}
		return "";
	}

}
