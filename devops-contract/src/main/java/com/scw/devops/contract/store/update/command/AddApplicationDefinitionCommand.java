package com.scw.devops.contract.store.update.command;


import com.scw.devops.contract.store.common.data.ApplicationDefinition;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class AddApplicationDefinitionCommand extends StoreUpdateCommand {

	final ApplicationDefinition data;

}
