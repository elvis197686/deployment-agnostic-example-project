package com.scw.devops.query.controller.request;

import static com.scw.devops.query.controller.DataStoreQueryBuilder.generateQuery;

import java.util.Optional;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.scw.devops.contract.store.query.data.VersionQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@ToString
@JsonDeserialize(builder = EnvironmentQueryInputFilter.Builder.class)
@Builder(builderClassName = "Builder")
public class EnvironmentQueryInputFilter {
	@Nullable
	final String name;
	@Nullable
	final String version;
	@Nullable
	final Boolean includePreview;
	@Nullable
	final Boolean autoStart;

	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder {
	}

	public static EnvironmentQueryInputFilter makeDefault() {
		return new EnvironmentQueryInputFilter(null, null, null, null);
	}

	public Optional<String> getWantedName() {
		return Optional.ofNullable(name);
	}

	public Optional<Boolean> isAutoStartWanted() {
		return Optional.ofNullable(autoStart);
	}

	public VersionQuery transformTo( final Optional<Integer> versionLimit ) {
		return generateQuery(versionLimit, version, includePreview, true);
	}

}
