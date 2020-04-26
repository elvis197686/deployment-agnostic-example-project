package com.scw.devops.collector.vcs.data;

import static com.scw.devops.collector.vcs.data.UrlFormatter.splitSourceRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scw.devops.collector.domain.RepositoryApplicationInstanceEntry;
import com.scw.devops.collector.domain.RepositoryProjectVersion;
import com.scw.devops.contract.store.common.data.EnvironmentProductDefinitionReference;

public class DeployYaml {

	private String version;
	private List<Application> applications;
	private List<RequiredProducts> requiredProducts;
	private List<RequiredInfrastructure> requiredInfrastructure;
	// Properties are stored by level, e.g. if a prop is defined over 3 lines,
	// there will be 3 levels in the map, regardless of the use of dots.
	private Map<String, Object> additionalProperties = new HashMap<>();

	public void setVersion(final String version) {
		this.version = version;
	}

	public void setApplications(final List<Application> applications) {
		this.applications = applications;
	}

	public void setRequiredProducts(final List<RequiredProducts> requiredProducts) {
		this.requiredProducts = requiredProducts;
	}

	public void setRequiredInfrastructure(final List<RequiredInfrastructure> requiredInfrastructure) {
		this.requiredInfrastructure = requiredInfrastructure;
	}

	public void setAdditionalProperties(final Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperties(final String key, final Object value) {
		additionalProperties.put(key, value);
	}

	public Map<String, Object> getAdditionalProperties() {
		return Collections.unmodifiableMap(additionalProperties);
	}

	public List<RepositoryLocation> getApplicationSourceRepositories() {
		if (applications != null) {
			return applications.stream().map(a -> a.getLocation()).filter(r -> r.isPresent()).map(s -> s.get())
					.collect(Collectors.toList());
		}
		return Arrays.asList();
	}

	public Collection<EnvironmentProductDefinitionReference> transformProductReferences() {
		if (requiredProducts != null) {
			return requiredProducts.stream().map(p -> p.transformTo()).collect(Collectors.toList());
		}
		return Arrays.asList();
	}

	public Collection<RepositoryApplicationInstanceEntry> transformApplications() {
		if (applications != null) {
			return applications.stream().map(a -> a.transformTo()).collect(Collectors.toList());
		}
		return Arrays.asList();
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Application {
		private String alias;
		private Definition definition;

		public void setAlias(final String alias) {
			this.alias = alias;
		}

		public void setDefinition(final Definition definition) {
			this.definition = definition;
		}

		@Override
		public String toString() {
			return "Application [alias=" + alias + ", definition=" + definition + "]";
		}

		public Optional<RepositoryLocation> getLocation() {
			return definition == null || definition.getSourceRepository() == null ? Optional.empty()
					: Optional.of(splitSourceRepository(definition.getSourceRepository().get()));
		}

		public RepositoryApplicationInstanceEntry transformTo() {
			// Version not set yet - set by values.yaml (or will error)
			return new RepositoryApplicationInstanceEntry( alias,
														   getLocation().map( l -> l.getProjectName().toLowerCase() ).orElse( null ) );
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Definition {
		private String name;
		private String sourceRepository;

		public void setName(final String name) {
			this.name = name;
		}

		public Optional<String> getSourceRepository() {
			return Optional.ofNullable(sourceRepository);
		}

		public void setSourceRepository(final String sourceRepository) {
			this.sourceRepository = sourceRepository;
		}

		@Override
		public String toString() {
			return "Definition [name=" + name + ", sourceRepository=" + sourceRepository + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class RequiredProducts {
		private String name;
		private String version;

		public void setName(final String name) {
			this.name = name;
		}

		public void setVersion(final String version) {
			this.version = version;
		}

		public EnvironmentProductDefinitionReference transformTo() {
			return new EnvironmentProductDefinitionReference( name,
															  RepositoryProjectVersion.fromSingleString( version ) );
		}

		@Override
		public String toString() {
			return "RequiredProducts [name=" + name + ", version=" + version + "]";
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class RequiredInfrastructure {
		private String name;

		public void setName(final String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "RequiredInfrastructure [name=" + name + "]";
		}
	}

	@Override
	public String toString() {
		return "DeployYaml [version=" + version + ", applications=" + applications +
			   ", requiredInfrastructure="
				+ requiredInfrastructure + ", additionalProperties=" + additionalProperties + "]";
	}

}
