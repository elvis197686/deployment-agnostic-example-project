package com.scw.devops.query.controller.request;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.scw.devops.contract.query.data.EnvironmentQueryInputFilter;
import com.scw.devops.contract.query.data.FilterDefaults;

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
		return ( this.inputEnvironmentFilter == null ) ? FilterDefaults.makeDefaultEnvironmentQueryInputFilter()
				: this.inputEnvironmentFilter;
	}

}
