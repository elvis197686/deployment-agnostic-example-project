package com.scw.devops.query.controller.request;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.scw.devops.contract.query.data.FilterDefaults;
import com.scw.devops.contract.query.data.StandardQueryInputFilter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@ToString
@JsonDeserialize(builder = StandardQueryBody.Builder.class)
@Builder(builderClassName = "Builder")
public class StandardQueryBody {

	@Nullable
	final StandardQueryInputFilter inputDefinitionFilter;

	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder {
	}

	public StandardQueryInputFilter getInputDefinitionFilter() {
		return ( this.inputDefinitionFilter == null ) ? FilterDefaults.makeDefaultStandardQueryInputFilter()
				: this.inputDefinitionFilter;
	}

}
