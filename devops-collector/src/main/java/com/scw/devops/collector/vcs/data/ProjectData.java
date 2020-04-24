package com.scw.devops.collector.vcs.data;

import static com.scw.devops.collector.vcs.data.UrlFormatter.constructHttpUrl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.scw.devops.collector.domain.RepositoryApplicationInstanceEntry;
import com.scw.devops.collector.domain.RepositoryProjectVersion;
import com.scw.devops.collector.domain.RepositoryType;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntry;
import com.scw.devops.contract.store.common.data.ConfigurationError;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.common.data.ProjectVersion;

public class ProjectData {

	private final RepositoryType repositoryType;
	private final String projectName;
	private final String branchOrTagName;
	private final boolean isPreview;
	private final DeployYaml rokitYaml;
	private final ValuesYaml valuesYaml;
	private final RequirementsYaml requirementsYaml;
	private final Exception projectError;

	public ProjectData(final RepositoryType repositoryType, final String projectName, final String branchOrTagName,
			final boolean isPreview, final DeployYaml rokitYaml, final ValuesYaml valuesYaml,
			final RequirementsYaml requirementsYaml) {
		this.repositoryType = repositoryType;
		this.projectName = projectName;
		this.branchOrTagName = branchOrTagName;
		this.isPreview = isPreview;
		this.rokitYaml = rokitYaml;
		this.valuesYaml = valuesYaml;
		this.requirementsYaml = requirementsYaml;
		this.projectError = null;
	}

	public ProjectData(final RepositoryType repositoryType, final String projectName, final String branchOrTagName,
			final boolean isPreview, final Exception projectError) {
		this.repositoryType = repositoryType;
		this.projectName = projectName;
		this.branchOrTagName = branchOrTagName;
		this.isPreview = isPreview;
		this.rokitYaml = null;
		this.valuesYaml = null;
		this.requirementsYaml = null;
		this.projectError = projectError;
	}

	// Transformers are better isolated from either data definition, but in this
	// instance that would mean adding meaningless getters
	public EnvironmentDefinition transformToEnvironment(final String gitlabUrl, final String groupName) {
		EnvironmentDefinition definition = new EnvironmentDefinition(
				new DefinitionBase(this.projectName, new ProjectVersion(this.branchOrTagName, this.isPreview),
						constructArbitraryEnvironmentProperties(), constructHttpUrl(gitlabUrl, groupName, projectName),
						constructErrorList()),
				(rokitYaml == null) ? Arrays.asList() : rokitYaml.transformProductReferences());
		return definition;
	}

	public ProductDefinition transformToProduct(final String gitlabUrl, final String groupName) {
		ProductDefinition definition = new ProductDefinition(new DefinitionBase(this.projectName,
				new ProjectVersion(this.branchOrTagName, this.isPreview), constructArbitraryEnvironmentProperties(),
				constructHttpUrl(gitlabUrl, groupName, projectName), constructErrorList()),
				getApplicationInstancesWithVersion(this.isPreview));
		return definition;
	}

	public ApplicationDefinition transformToApplication(final String gitlabUrl, final String groupName) {
		ApplicationDefinition definition = new ApplicationDefinition(new DefinitionBase(this.projectName.toLowerCase(),
				new ProjectVersion(this.branchOrTagName, this.isPreview), constructArbitraryEnvironmentProperties(),
				constructHttpUrl(gitlabUrl, groupName, projectName), constructErrorList()));
		return definition;
	}

	public String getProjectName() {
		return projectName;
	}

	public List<RepositoryLocation> getApplicationRepositories() {
		if (rokitYaml != null) {
			return rokitYaml.getApplicationSourceRepositories();
		}

		return Arrays.asList();
	}

	public String getIdentifier() {
		return projectName + ":" + branchOrTagName;
	}

	@Override
	public String toString() {
		return "ProjectData [repositoryType=" + repositoryType + ", branchOrTagName=" + branchOrTagName + ", rokitYaml="
				+ rokitYaml + ", requirementsYaml=" + requirementsYaml + ", projectError=" + projectError + "]";
	}

	private Collection<ApplicationInstanceEntry> getApplicationInstancesWithVersion(final boolean isPreviewProduct) {
		Collection<RepositoryApplicationInstanceEntry> entriesWithoutVersion = ( rokitYaml == null ) ? Arrays.asList()
				: rokitYaml.transformApplications();
		return entriesWithoutVersion.stream().map(e -> addVersion(e, isPreviewProduct)).collect(Collectors.toList());
	}

	private ApplicationInstanceEntry addVersion( final RepositoryApplicationInstanceEntry entryWithoutVersion, final boolean forcePreview ) {
		return entryWithoutVersion
			.transformTo( ( forcePreview ) ? RepositoryProjectVersion.getPreviewString()
										   : valuesYaml.getVersionString( entryWithoutVersion.getValuesYamlName() ) );
	}

	private Map<String, Object> constructArbitraryEnvironmentProperties() {
		// Just autoStart at the moment, but anything that is a straight property is a
		// candidate to add to this list
		return this.rokitYaml == null ? new HashMap<>() : this.rokitYaml.getAdditionalProperties();
	}

	private Collection<ConfigurationError> constructErrorList() {
		if (this.projectError == null) {
			return Arrays.asList();
		}
		return Arrays.asList(new ConfigurationError(this.projectError.getMessage()));
	}

}
