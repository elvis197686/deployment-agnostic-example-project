package com.scw.devops.deploy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.scw.devops.contract.collector.data.OutputVoid;
import com.scw.devops.contract.query.command.DevopsQueryCommand;
import com.scw.devops.contract.query.command.GetApplicationsCommand;
import com.scw.devops.contract.query.command.GetEnvironmentsCommand;
import com.scw.devops.contract.query.command.GetOrphanedApplicationsSinceRestartCommand;
import com.scw.devops.contract.query.command.GetPreviousApplicationsDefinitionCommand;
import com.scw.devops.contract.query.command.GetProductsCommand;
import com.scw.devops.contract.query.command.GetProductsInApplicationCommand;
import com.scw.devops.contract.query.command.GetProductsInEnvironmentCommand;
import com.scw.devops.contract.query.command.ReturnApplicationDefinition;
import com.scw.devops.contract.query.command.ReturnApplicationDefinitions;
import com.scw.devops.contract.query.command.ReturnEnvironmentDefinitions;
import com.scw.devops.contract.query.command.ReturnProductDefinitions;
import com.scw.devops.contract.query.data.ApplicationDef;
import com.scw.devops.contract.query.data.ConfigurationErrorData;
import com.scw.devops.contract.query.data.EnvironmentDef;
import com.scw.devops.contract.query.data.EnvironmentQueryInputFilter;
import com.scw.devops.contract.query.data.ProductDef;
import com.scw.devops.contract.query.data.StandardQueryInputFilter;
import com.scw.devops.contract.serialisation.AddApplicationDefinitionCommandMixin;
import com.scw.devops.contract.serialisation.AddEnvironmentDefinitionCommandMixin;
import com.scw.devops.contract.serialisation.AddProductDefinitionCommandMixin;
import com.scw.devops.contract.serialisation.ApplicationDefMixin;
import com.scw.devops.contract.serialisation.ApplicationDefinitionMixin;
import com.scw.devops.contract.serialisation.ApplicationInstanceEntryMixin;
import com.scw.devops.contract.serialisation.ConfigurationErrorDataMixin;
import com.scw.devops.contract.serialisation.ConfigurationErrorMixin;
import com.scw.devops.contract.serialisation.DefinitionBaseMixin;
import com.scw.devops.contract.serialisation.DevopsQueryCommandMixin;
import com.scw.devops.contract.serialisation.EnvironmentDefMixin;
import com.scw.devops.contract.serialisation.EnvironmentDefinitionMixin;
import com.scw.devops.contract.serialisation.EnvironmentProductDefinitionReferenceMixin;
import com.scw.devops.contract.serialisation.EnvironmentQueryInputFilterMixin;
import com.scw.devops.contract.serialisation.GetAllApplicationDefinitionsCommandMixin;
import com.scw.devops.contract.serialisation.GetAllEnvironmentDefinitionsCommandMixin;
import com.scw.devops.contract.serialisation.GetAllProductDefinitionsCommandMixin;
import com.scw.devops.contract.serialisation.GetApplicationDefinitionCommandMixin;
import com.scw.devops.contract.serialisation.GetApplicationsCommandMixin;
import com.scw.devops.contract.serialisation.GetEnvironmentsCommandMixin;
import com.scw.devops.contract.serialisation.GetEnvironmentsWithProductDeployedCommandMixin;
import com.scw.devops.contract.serialisation.GetOrphanedApplicationsSinceRestartCommandMixin;
import com.scw.devops.contract.serialisation.GetPreviousApplicationDefinitionCommandMixin;
import com.scw.devops.contract.serialisation.GetPreviousApplicationsDefinitionCommandMixin;
import com.scw.devops.contract.serialisation.GetProductApplicationReferencesCommandMixin;
import com.scw.devops.contract.serialisation.GetProductDefinitionsForApplicationCommandMixin;
import com.scw.devops.contract.serialisation.GetProductDefinitionsForEnvironmentCommandMixin;
import com.scw.devops.contract.serialisation.GetProductsCommandMixin;
import com.scw.devops.contract.serialisation.GetProductsInApplicationCommandMixin;
import com.scw.devops.contract.serialisation.GetProductsInEnvironmentCommandMixin;
import com.scw.devops.contract.serialisation.OutputApplicationDefinitionMixin;
import com.scw.devops.contract.serialisation.OutputApplicationDefinitionsMixin;
import com.scw.devops.contract.serialisation.OutputApplicationInstancesMixin;
import com.scw.devops.contract.serialisation.OutputEnvironmentDefinitionsMixin;
import com.scw.devops.contract.serialisation.OutputProductDefinitionsMixin;
import com.scw.devops.contract.serialisation.OutputVoidMixin;
import com.scw.devops.contract.serialisation.ProductDefMixin;
import com.scw.devops.contract.serialisation.ProductDefinitionMixin;
import com.scw.devops.contract.serialisation.ProjectVersionMixin;
import com.scw.devops.contract.serialisation.ReturnApplicationDefinitionMixin;
import com.scw.devops.contract.serialisation.ReturnApplicationsDefinitionMixin;
import com.scw.devops.contract.serialisation.ReturnEnvironmentsDefinitionMixin;
import com.scw.devops.contract.serialisation.ReturnProductsDefinitionMixin;
import com.scw.devops.contract.serialisation.StandardQueryInputFilterMixin;
import com.scw.devops.contract.serialisation.StoreQueryCommandMixin;
import com.scw.devops.contract.serialisation.StoreQueryCommandResultMixin;
import com.scw.devops.contract.serialisation.StoreUpdateCommandMixin;
import com.scw.devops.contract.store.common.data.ApplicationDefinition;
import com.scw.devops.contract.store.common.data.ApplicationInstanceEntry;
import com.scw.devops.contract.store.common.data.ConfigurationError;
import com.scw.devops.contract.store.common.data.DefinitionBase;
import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.common.data.EnvironmentProductDefinitionReference;
import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.query.command.GetAllApplicationDefinitionsCommand;
import com.scw.devops.contract.store.query.command.GetAllEnvironmentDefinitionsCommand;
import com.scw.devops.contract.store.query.command.GetAllProductDefinitionsCommand;
import com.scw.devops.contract.store.query.command.GetApplicationDefinitionCommand;
import com.scw.devops.contract.store.query.command.GetEnvironmentsWithProductDeployedCommand;
import com.scw.devops.contract.store.query.command.GetPreviousApplicationDefinitionCommand;
import com.scw.devops.contract.store.query.command.GetProductApplicationReferencesCommand;
import com.scw.devops.contract.store.query.command.GetProductDefinitionsForApplicationCommand;
import com.scw.devops.contract.store.query.command.GetProductDefinitionsForEnvironmentCommand;
import com.scw.devops.contract.store.query.command.OutputApplicationDefinition;
import com.scw.devops.contract.store.query.command.OutputApplicationDefinitions;
import com.scw.devops.contract.store.query.command.OutputApplicationInstances;
import com.scw.devops.contract.store.query.command.OutputEnvironmentDefinitions;
import com.scw.devops.contract.store.query.command.OutputProductDefinitions;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.update.command.AddApplicationDefinitionCommand;
import com.scw.devops.contract.store.update.command.AddEnvironmentDefinitionCommand;
import com.scw.devops.contract.store.update.command.AddProductDefinitionCommand;
import com.scw.devops.contract.store.update.command.StoreUpdateCommand;
import com.scw.devops.domain.projectversion.ProjectVersion;

@Configuration
public class ApplicationConfiguration {

	@Bean
	@Scope("prototype")
	public Logger logger(final InjectionPoint injectionPoint) {
		Class<?> clazz = null;
		if ( injectionPoint.getMethodParameter() != null ) {
			clazz = injectionPoint.getMethodParameter().getContainingClass();
		}
		else if ( injectionPoint.getField() != null ) {
			clazz = injectionPoint.getField().getDeclaringClass();
		}
		if ( clazz == null ) {
			return LoggerFactory.getILoggerFactory().getLogger( injectionPoint.getDeclaredType().getName() );
		}
		return LoggerFactory.getLogger( clazz );
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.registerModule( new Jdk8Module() );
		// Code to allow serialisation and deserialisation of contracts without Jackson annotation
		jsonObjectMapper.setVisibility( jsonObjectMapper.getSerializationConfig()
			.getDefaultVisibilityChecker()
			.withFieldVisibility( JsonAutoDetect.Visibility.ANY )
			.withGetterVisibility( JsonAutoDetect.Visibility.NONE )
			.withSetterVisibility( JsonAutoDetect.Visibility.NONE )
			.withCreatorVisibility( JsonAutoDetect.Visibility.NONE ) );
		// Note: These are required until Jackson implements auto-deserialisation for classes with a single constructor
		// Store
		jsonObjectMapper.addMixIn( ProjectVersion.class, ProjectVersionMixin.class );
		jsonObjectMapper.addMixIn( ConfigurationError.class, ConfigurationErrorMixin.class );
		jsonObjectMapper.addMixIn( DefinitionBase.class, DefinitionBaseMixin.class );
		jsonObjectMapper.addMixIn( ApplicationInstanceEntry.class, ApplicationInstanceEntryMixin.class );
		jsonObjectMapper.addMixIn( EnvironmentProductDefinitionReference.class, EnvironmentProductDefinitionReferenceMixin.class );
		jsonObjectMapper.addMixIn( ApplicationDefinition.class, ApplicationDefinitionMixin.class );
		jsonObjectMapper.addMixIn( ProductDefinition.class, ProductDefinitionMixin.class );
		jsonObjectMapper.addMixIn( EnvironmentDefinition.class, EnvironmentDefinitionMixin.class );
		jsonObjectMapper.addMixIn( AddApplicationDefinitionCommand.class, AddApplicationDefinitionCommandMixin.class );
		jsonObjectMapper.addMixIn( AddProductDefinitionCommand.class, AddProductDefinitionCommandMixin.class );
		jsonObjectMapper.addMixIn( AddEnvironmentDefinitionCommand.class, AddEnvironmentDefinitionCommandMixin.class );
		jsonObjectMapper.addMixIn( StoreUpdateCommand.class, StoreUpdateCommandMixin.class );
		jsonObjectMapper.addMixIn( GetAllEnvironmentDefinitionsCommand.class, GetAllEnvironmentDefinitionsCommandMixin.class );
		jsonObjectMapper.addMixIn( GetAllProductDefinitionsCommand.class, GetAllProductDefinitionsCommandMixin.class );
		jsonObjectMapper.addMixIn( GetAllApplicationDefinitionsCommand.class, GetAllApplicationDefinitionsCommandMixin.class );
		jsonObjectMapper.addMixIn( GetApplicationDefinitionCommand.class, GetApplicationDefinitionCommandMixin.class );
		jsonObjectMapper.addMixIn( GetEnvironmentsWithProductDeployedCommand.class, GetEnvironmentsWithProductDeployedCommandMixin.class );
		jsonObjectMapper.addMixIn( GetPreviousApplicationDefinitionCommand.class, GetPreviousApplicationDefinitionCommandMixin.class );
		jsonObjectMapper.addMixIn( GetProductApplicationReferencesCommand.class, GetProductApplicationReferencesCommandMixin.class );
		jsonObjectMapper.addMixIn( GetProductDefinitionsForApplicationCommand.class, GetProductDefinitionsForApplicationCommandMixin.class );
		jsonObjectMapper.addMixIn( GetProductDefinitionsForEnvironmentCommand.class, GetProductDefinitionsForEnvironmentCommandMixin.class );
		jsonObjectMapper.addMixIn( StoreQueryCommand.class, StoreQueryCommandMixin.class );
		jsonObjectMapper.addMixIn( OutputApplicationDefinition.class, OutputApplicationDefinitionMixin.class );
		jsonObjectMapper.addMixIn( OutputApplicationDefinitions.class, OutputApplicationDefinitionsMixin.class );
		jsonObjectMapper.addMixIn( OutputApplicationInstances.class, OutputApplicationInstancesMixin.class );
		jsonObjectMapper.addMixIn( OutputProductDefinitions.class, OutputProductDefinitionsMixin.class );
		jsonObjectMapper.addMixIn( OutputEnvironmentDefinitions.class, OutputEnvironmentDefinitionsMixin.class );
		jsonObjectMapper.addMixIn( StoreQueryCommandResult.class, StoreQueryCommandResultMixin.class );
		// Query
		jsonObjectMapper.addMixIn( EnvironmentQueryInputFilter.class, EnvironmentQueryInputFilterMixin.class );
		jsonObjectMapper.addMixIn( StandardQueryInputFilter.class, StandardQueryInputFilterMixin.class );
		jsonObjectMapper.addMixIn( GetEnvironmentsCommand.class, GetEnvironmentsCommandMixin.class );
		jsonObjectMapper.addMixIn( GetProductsCommand.class, GetProductsCommandMixin.class );
		jsonObjectMapper.addMixIn( GetApplicationsCommand.class, GetApplicationsCommandMixin.class );
		jsonObjectMapper.addMixIn( GetOrphanedApplicationsSinceRestartCommand.class, GetOrphanedApplicationsSinceRestartCommandMixin.class );
		jsonObjectMapper.addMixIn( GetPreviousApplicationsDefinitionCommand.class, GetPreviousApplicationsDefinitionCommandMixin.class );
		jsonObjectMapper.addMixIn( GetProductsInApplicationCommand.class, GetProductsInApplicationCommandMixin.class );
		jsonObjectMapper.addMixIn( GetProductsInEnvironmentCommand.class, GetProductsInEnvironmentCommandMixin.class );
		jsonObjectMapper.addMixIn( ConfigurationErrorData.class, ConfigurationErrorDataMixin.class );
		jsonObjectMapper.addMixIn( ApplicationDef.class, ApplicationDefMixin.class );
		jsonObjectMapper.addMixIn( ProductDef.class, ProductDefMixin.class );
		jsonObjectMapper.addMixIn( EnvironmentDef.class, EnvironmentDefMixin.class );
		jsonObjectMapper.addMixIn( ReturnApplicationDefinition.class, ReturnApplicationDefinitionMixin.class );
		jsonObjectMapper.addMixIn( ReturnApplicationDefinitions.class, ReturnApplicationsDefinitionMixin.class );
		jsonObjectMapper.addMixIn( ReturnProductDefinitions.class, ReturnProductsDefinitionMixin.class );
		jsonObjectMapper.addMixIn( ReturnEnvironmentDefinitions.class, ReturnEnvironmentsDefinitionMixin.class );
		jsonObjectMapper.addMixIn( DevopsQueryCommand.class, DevopsQueryCommandMixin.class );
		// Collector
		jsonObjectMapper.addMixIn( OutputVoid.class, OutputVoidMixin.class );
		return jsonObjectMapper;
	}

}
