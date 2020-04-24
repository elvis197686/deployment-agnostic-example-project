package com.scw.devops.query.controller.request;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

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
		return (this.inputDefinitionFilter == null) ? StandardQueryInputFilter.makeDefault()
				: this.inputDefinitionFilter;
	}

}
