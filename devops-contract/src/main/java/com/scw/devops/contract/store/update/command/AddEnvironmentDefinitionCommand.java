package com.scw.devops.contract.store.update.command;


import com.scw.devops.contract.store.common.data.EnvironmentDefinition;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class AddEnvironmentDefinitionCommand extends StoreUpdateCommand {

	final EnvironmentDefinition data;

	Exception ex;

}
