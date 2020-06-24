package com.scw.devops.contract.store.update.command.addenvironment;


import com.scw.devops.contract.store.common.data.EnvironmentDefinition;
import com.scw.devops.contract.store.update.command.StoreUpdateCommand;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class AddEnvironmentDefinitionCommand extends StoreUpdateCommand {

	final EnvironmentDefinition data;

	Exception ex;

}
