package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scw.devops.contract.store.query.data.VersionQuery;

public abstract class GetAllEnvironmentDefinitionsCommandMixin {

    @JsonCreator
    public GetAllEnvironmentDefinitionsCommandMixin(
            @JsonProperty("versionQuery") final VersionQuery versionQuery) {
        System.out.println("Wont be called");
	}
}
