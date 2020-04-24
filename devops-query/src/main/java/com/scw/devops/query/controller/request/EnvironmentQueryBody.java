package com.scw.devops.query.controller.request;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@ToString
@JsonDeserialize(builder = EnvironmentQueryBody.Builder.class)
@Builder(builderClassName = "Builder")
public class EnvironmentQueryBody {

	@Nullable
	final EnvironmentQueryInputFilter inputEnvironmentFilter;

	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder {
	}

	public EnvironmentQueryInputFilter getInputFilter() {
		return (this.inputEnvironmentFilter == null) ? EnvironmentQueryInputFilter.makeDefault()
				: this.inputEnvironmentFilter;
	}

}
