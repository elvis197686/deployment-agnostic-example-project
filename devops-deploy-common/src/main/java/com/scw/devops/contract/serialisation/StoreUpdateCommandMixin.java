package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo( use = Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type" )
//@JsonSubTypes( { @Type( value = GetAllApplicationDefinitionsCommand.class ) } )
public abstract class StoreUpdateCommandMixin {
}
