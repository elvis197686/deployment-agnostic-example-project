package com.scw.devops.collector.domain;

import static com.scw.devops.collector.domain.UrlFormatter.constructHttpUrl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.scw.devops.collector.vcs.data.DeployYaml;
import com.scw.devops.collector.vcs.data.RequirementsYaml;
import com.scw.devops.collector.vcs.data.ValuesYaml;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntry;
import com.scw.devops.contract.store.common.data.ConfigurationError;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.domain.projectversion.ProjectVersion;

public class ProjectData {

	private final RepositoryType repositoryType;
	private final String projectName;
	private final String branchOrTagName;
	private final ProjectVersion projectVersion;
	private final DeployYaml deployYaml;
	private final ValuesYaml valuesYaml;
	private final RequirementsYaml requirementsYaml;
	private final Exception projectError;

	public ProjectData(final RepositoryType repositoryType, final String projectName, final String branchOrTagName,
						final ProjectVersion projectVersion, final DeployYaml deployYaml,
						final ValuesYaml valuesYaml,
			final RequirementsYaml requirementsYaml) {
		this.repositoryType = repositoryType;
		this.projectName = projectName;
		this.branchOrTagName = branchOrTagName;
		this.projectVersion = projectVersion;
		this.deployYaml = deployYaml;
		this.valuesYaml = valuesYaml;
		this.requirementsYaml = requirementsYaml;
		this.projectError = null;
	}

	public ProjectData(final RepositoryType repositoryType, final String projectName, final String branchOrTagName,
						final ProjectVersion projectVersion, final Exception projectError ) {
		this.repositoryType = repositoryType;
		this.projectName = projectName;
		this.branchOrTagName = branchOrTagName;
		this.projectVersion = projectVersion;
		this.deployYaml = null;
		this.valuesYaml = null;
		this.requirementsYaml = null;
		this.projectError = projectError;
	}

	// Transformers are better isolated from either data definition, but in this
	// instance that would mean adding meaningless getters
	public EnvironmentDefinition transformToEnvironment(final String gitlabUrl, final String groupName) {
		EnvironmentDefinition definition = new EnvironmentDefinition(
																	  new DefinitionBase( this.projectName,
																						  this.projectVersion,
						constructArbitraryEnvironmentProperties(), constructHttpUrl(gitlabUrl, groupName, projectName),
						constructErrorList()),
				(deployYaml == null) ? Arrays.asList() : deployYaml.transformProductReferences());
		return definition;
	}

	public ProductDefinition transformToProduct(final String gitlabUrl, final String groupName) {
		ProductDefinition definition = new ProductDefinition(new DefinitionBase(this.projectName,
																				  this.projectVersion,
																				  constructArbitraryEnvironmentProperties(),
				constructHttpUrl(gitlabUrl, groupName, projectName), constructErrorList()),
															  getApplicationInstancesWithVersion( this.projectVersion ) );
		return definition;
	}

	public ApplicationDefinition transformToApplication(final String gitlabUrl, final String groupName) {
		ApplicationDefinition definition = new ApplicationDefinition(new DefinitionBase(this.projectName.toLowerCase(),
																						  this.projectVersion,
																						  constructArbitraryEnvironmentProperties(),
				constructHttpUrl(gitlabUrl, groupName, projectName), constructErrorList()));
		return definition;
	}

	public String getProjectName() {
		return projectName;
	}

	public List<RepositoryLocation> getApplicationRepositories() {
		if (deployYaml != null) {
			return deployYaml.getApplicationSourceRepositories();
		}

		return Arrays.asList();
	}

	public String getIdentifier() {
		return projectName + ":" + branchOrTagName;
	}

	@Override
	public String toString() {
		return "ProjectData [repositoryType=" + repositoryType + ", branchOrTagName=" + branchOrTagName +
			   ", deployYaml=" + deployYaml + ", requirementsYaml=" + requirementsYaml + ", projectError=" + projectError + "]";
	}

	private Collection<ApplicationInstanceEntry> getApplicationInstancesWithVersion( final ProjectVersion parentProjectVersion ) {
		Collection<RepositoryApplicationInstanceEntry> entriesWithoutVersion = ( deployYaml == null ) ? Arrays.asList()
				: deployYaml.transformApplications();
		return entriesWithoutVersion.stream().map( e -> addVersion( e, parentProjectVersion ) ).collect( Collectors.toList() );
	}

	private ApplicationInstanceEntry addVersion( final RepositoryApplicationInstanceEntry entryWithoutVersion, final ProjectVersion parentProjectVersion ) {
		return entryWithoutVersion
			.transformTo( ProjectVersion.combineParentAndOwnVersion( parentProjectVersion,
																	 valuesYaml.getVersionString( entryWithoutVersion.getValuesYamlName() ) ) );
	}

	private Map<String, Object> constructArbitraryEnvironmentProperties() {
		// Just autoStart at the moment, but anything that is a straight property is a
		// candidate to add to this list
		return this.deployYaml == null ? new HashMap<>() : this.deployYaml.getAdditionalProperties();
	}

	private Collection<ConfigurationError> constructErrorList() {
		if (this.projectError == null) {
			return Arrays.asList();
		}
		return Arrays.asList(new ConfigurationError(this.projectError.getMessage()));
	}

}
