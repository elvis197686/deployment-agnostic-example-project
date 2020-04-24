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
@JsonDeserialize(builder = StandardQueryInputFilter.Builder.class)
@Builder(builderClassName = "Builder")
public class StandardQueryInputFilter {

	private static final String ANY = "ANY";
	private static final String VALID_ONLY = "VALID";
	private static final String CLASS_UNSET = "NOT SET";

	@Nullable
	final String name;
	@Nullable
	final String version;
	@Nullable
	final Boolean includePreview;
	@Nullable
	final String validity;
	@Nullable
	final String productClass;

	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder {
	}

	public static StandardQueryInputFilter makeDefault() {
		return new StandardQueryInputFilter(null, null, null, null, null);
	}

	public VersionQuery transformTo( final Optional<Integer> versionLimit ) {
		return generateQuery(versionLimit, version, includePreview, false);
	}

	public Optional<String> getWantedName() {
		return Optional.ofNullable(name);
	}

	public Optional<Boolean> getWantedValidity() {
		if (validity != null && !validity.equals(ANY)) {
			return Optional.of(validity.equals(VALID_ONLY) ? true : false);
		}
		return Optional.empty();
	}

	public boolean getUnsetClassOnly() {
		return productClass != null && productClass.equals(CLASS_UNSET);
	}

	public Optional<String> getWantedClass() {
		return Optional.ofNullable(productClass);
	}

}
