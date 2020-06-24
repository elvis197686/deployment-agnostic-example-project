package com.scw.devops.contract.store.update.command.addproduct;

import com.scw.devops.contract.store.common.data.ProductDefinition;
import com.scw.devops.contract.store.update.command.StoreUpdateCommand;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class AddProductDefinitionCommand extends StoreUpdateCommand {

	final ProductDefinition data;

	Exception ex;

}
