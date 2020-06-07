package com.scw.devops.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scw.devops.contract.store.DataStoreReaderImpl;
import com.scw.devops.contract.store.DataStoreUpdateImpl;
import com.scw.devops.contract.store.query.command.StoreQueryCommand;
import com.scw.devops.contract.store.query.command.StoreQueryCommandResult;
import com.scw.devops.contract.store.update.command.StoreUpdateCommand;

@RestController
public class StoreController {

	private final DataStoreUpdateImpl dataStoreUpdate;
	private final DataStoreReaderImpl dataStoreReader;

	@Autowired
	public StoreController( final DataStoreUpdateImpl dataStoreUpdate, final DataStoreReaderImpl dataStoreReader ) {
		this.dataStoreUpdate = dataStoreUpdate;
		this.dataStoreReader = dataStoreReader;
	}

	@PostMapping( value = "/update", produces = "application/json" )
	@ResponseBody
	public void updateCommand( @RequestBody final StoreUpdateCommand command ) {
		dataStoreUpdate.doCommand( command );
	}

	@PostMapping( value = "/query", produces = "application/json" )
	@ResponseBody
	public StoreQueryCommandResult queryCommand( @RequestBody final StoreQueryCommand command ) {
		return dataStoreReader.doCommand( command );
	}

}