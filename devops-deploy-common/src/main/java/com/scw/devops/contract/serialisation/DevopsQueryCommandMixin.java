package com.scw.devops.contract.serialisation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.scw.devops.contract.query.command.getenvironments.GetEnvironmentsCommand;

@JsonTypeInfo( use = Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type" )
@JsonSubTypes( { @Type( value = GetEnvironmentsCommand.class ) } )
public abstract class DevopsQueryCommandMixin {
}
