package com.scw.devops.collector.vcs.data;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class ValuesYaml {

	private static final String IMAGE_PATH = "image";
	private static final String TAG_PATH = "tag";

	private static final String VERSION_NOT_FOUND = "NO_VERSION_FOUND";

	// Properties are stored by level, e.g. if a prop is defined over 3 lines,
	// there will be 3 levels in the map, regardless of the use of dots.
	private Map<String, Object> additionalProperties = new HashMap<>();

	@JsonAnySetter
	public void setAdditionalProperties(String key, Object value) {
		additionalProperties.put(key, value);
	}

	public String getVersionString(String applicationName) {
		Map<String, Object> appProperties = (Map<String, Object>) additionalProperties.get(applicationName);
		if (appProperties != null) {
			Map<String, Object> appImageProperties = (Map<String, Object>) appProperties.get(IMAGE_PATH);
			if (appImageProperties != null) {
				String foundVersion = (String) appImageProperties.get(TAG_PATH);
				if (foundVersion != null) {
					return foundVersion;
				}
			}
		}
		return VERSION_NOT_FOUND;
	}

	@Override
	public String toString() {
		return "ValuesYaml [additionalProperties=" + additionalProperties + "]";
	}

}
