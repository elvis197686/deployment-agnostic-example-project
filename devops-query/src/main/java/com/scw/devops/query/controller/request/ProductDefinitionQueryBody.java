package com.scw.devops.query.controller.request;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@ToString
@JsonDeserialize(builder = ProductDefinitionQueryBody.Builder.class)
@Builder(builderClassName = "Builder")
public class ProductDefinitionQueryBody {

	@Nullable
	final ProductDefinitionQueryInputFilter inputProductDefinitionFilter;

	@JsonPOJOBuilder(withPrefix = "")
	public static class Builder {
	}

	public ProductDefinitionQueryInputFilter getInputProductDefinitionFilter() {
		return (this.inputProductDefinitionFilter == null) ? ProductDefinitionQueryInputFilter.makeDefault()
				: this.inputProductDefinitionFilter;
	}

}
