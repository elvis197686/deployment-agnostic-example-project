package com.scw.devops.contract.query.data;

import java.util.Optional;

public class StandardQueryInputFilterProcessor {

	private static final String ANY = "ANY";
	private static final String VALID_ONLY = "VALID";

	private static final String CLASS_UNSET = "NOT SET";

	public static boolean getUnsetClassOnly( final StandardQueryInputFilter queryFilter ) {
		return queryFilter.productClass != null && queryFilter.productClass.equals( CLASS_UNSET );
	}

	public static Optional<String> getWantedClass( final StandardQueryInputFilter queryFilter ) {
		return queryFilter.productClass;
	}

	public static Optional<String> getWantedName( final StandardQueryInputFilter queryFilter ) {
		return queryFilter.name;
	}

	public static Optional<Boolean> getWantedValidity( final StandardQueryInputFilter queryFilter ) {
		if ( queryFilter.validity.isPresent() && !queryFilter.validity.equals( ANY ) ) {
			return Optional.of( queryFilter.validity.equals( VALID_ONLY ) ? true : false );
		}
		return Optional.empty();
	}

}
