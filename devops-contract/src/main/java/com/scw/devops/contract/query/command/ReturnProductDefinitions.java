package com.scw.devops.contract.query.command;

import java.util.List;

import com.scw.devops.contract.query.data.ProductDef;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ReturnProductDefinitions extends DevopsQueryCommandResult {

	private final List<ProductDef> products;

	List<ProductDef> getResult() {
		return products;
	}

}
