package com.scw.devops.application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// TODO delete
public class AutowiringProvider {

	@SuppressWarnings( "unchecked" )
	public static <T> T getProvider( final Class<T> clazz ) {
		final String providerImpl = "com.scw.devops.application.AutowiringProviderImpl";
		Class<?> implClass = null;
		try {
			implClass = Class.forName( providerImpl );
		}
		catch ( ClassNotFoundException l_ex ) {
			throw new RuntimeException( "No implementation class: " + providerImpl );
		}

		// Call static execute() method
		try {
			Method executeMethod = implClass.getMethod( "getProvider" );
			Object impl = executeMethod.invoke( null );
			if ( !clazz.isAssignableFrom( impl.getClass() ) ) {
				throw new RuntimeException( "Invalid class type returned: " + providerImpl + ", Type: " + clazz );
			}
			return (T) impl;
		}
		catch ( NoSuchMethodException ex ) {
			throw new RuntimeException( "No execute method: " + providerImpl, ex );
		}
		catch ( SecurityException ex ) {
			throw new RuntimeException( "Cannot access execute method: " + providerImpl, ex );
		}
		catch ( IllegalAccessException ex ) {
			throw new RuntimeException( "Unable to access execute method: " + providerImpl, ex );
		}
		catch ( IllegalArgumentException ex ) {
			throw new RuntimeException( "Invalid arguments to execute method: " + providerImpl, ex );
		}
		catch ( InvocationTargetException ex ) {
			throw new RuntimeException( "Unable to invoke execute method: " + providerImpl, ex );
		}
	}

}
