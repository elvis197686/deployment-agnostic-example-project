package com.scw.devops.query.controller.request;

import java.util.Optional;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@ToString
@JsonDeserialize(builder = ProductDefinitionQueryInputFilter.Builder.class)
@Builder(builderClassName = "Builder")
public class ProductDefinitionQueryInputFilter {
	@Nullable
	final String productClass;

	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder {
	}

	public static ProductDefinitionQueryInputFilter makeDefault() {
		return new ProductDefinitionQueryInputFilter(null);
	}

	public Optional<String> getWantedClass() {
		return Optional.ofNullable(productClass);
	}

}
