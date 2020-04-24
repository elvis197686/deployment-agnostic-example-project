package com.scw.devops.contract.store.common.data;

import java.util.List;
import java.util.stream.Collectors;

import com.scw.devops.query.controller.response.Environment;
import com.scw.devops.query.controller.response.EnvironmentDef;

public class ClientEnvironmentDefinitionTransformer {

	public static Environment transformTo( final EnvironmentDefinition def ) {
		return new Environment( def.base.name,
								SharedProjectVersionProcessor
									.getSingleVersionString( def.base.version ),
								transformToDefinition( def ),
								ClientConfigurationErrorDataTransformer.transformTo( def.base.errors ) );
	}

	public static EnvironmentDef transformToDefinition( final EnvironmentDefinition def ) {
		EnvironmentDef response = new EnvironmentDef( def.base.name,
													  SharedProjectVersionProcessor.getSingleVersionString( def.base.version ),
													  ClientEnvironmentDefinitionProcessor
														  .getAutoStart( def ),
													  def.base.sourceRepository,
													  ClientConfigurationErrorDataTransformer.transformToSingleError( def.base.errors ) );
		return response;
	}

	public static List<EnvironmentDef> transformTo( final List<EnvironmentDefinition> defs ) {
		if ( defs == null ) {
			return null;
		}
		return defs.stream().map( ClientEnvironmentDefinitionTransformer::transformToDefinition ).collect( Collectors.toList() );
	}

}
