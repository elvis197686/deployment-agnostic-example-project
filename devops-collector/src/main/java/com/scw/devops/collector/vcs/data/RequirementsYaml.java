package com.scw.devops.collector.vcs.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class RequirementsYaml {
	private List<Dependencies> dependencies;
	// Properties are stored by level, e.g. if a prop is defined over 3 lines,
	// there will be 3 levels in the map, regardless of the use of dots.
	// Arrays of values (as denoted by thre dash) are - as you would -expect -
	// stored in a list of maps.
	private Map<String, Object> additionalProperties = new HashMap<>();

	public void setDependencies(List<Dependencies> dependencies) {
		this.dependencies = dependencies;
	}

	@JsonAnySetter
	public void setAdditionalProperties(String key, Object value) {
		additionalProperties.put(key, value);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Dependencies {
		private String name;
		private String alias;
		private String version;
		private String repository;

		public void setName(String name) {
			this.name = name;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public void setRepository(String repository) {
			this.repository = repository;
		}

		@Override
		public String toString() {
			return "Dependencies [name=" + name + ", alias=" + alias + ", version=" + version + ", repository="
					+ repository + "]";
		}
	}

	@Override
	public String toString() {
		return "RequirementsYaml [dependencies=" + dependencies + ", additionalProperties=" + additionalProperties
				+ "]";
	}

}
