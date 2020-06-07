package com.scw.devops.contract.collector.data;


public class OutputIngestionRequestProcessor {

	public static void processResult( final OutputIngestionRequest outputIngestionRequest ) throws Throwable {
		if ( outputIngestionRequest.th != null ) {
			throw outputIngestionRequest.th;
		}
	}

}
