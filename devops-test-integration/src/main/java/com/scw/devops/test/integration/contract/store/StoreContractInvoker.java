package com.scw.devops.test.integration.contract.store;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StoreContractInvoker {

	public static <C, S> void doCommand( final C command, final S serviceClass ) {
		Class<?> implClass = null;
		try {
			implClass = Class.forName(command.getClass().getName() + "Impl");
		}
		catch ( ClassNotFoundException l_ex ) {
			throw new RuntimeException( "No implementation class: " + command.getClass().getName() );
		}

		// Call static execute() method
		try {
			Method executeMethod = implClass.getMethod( "execute", serviceClass.getClass(), command.getClass() );
			executeMethod.invoke( null, serviceClass, command );
		}
		catch ( NoSuchMethodException ex ) {
			throw new RuntimeException( "No execute method: " + command.getClass().getName(), ex );
		}
		catch ( SecurityException ex ) {
			throw new RuntimeException( "Cannot access execute method: " + command.getClass().getName(), ex );
		}
		catch ( IllegalAccessException ex ) {
			throw new RuntimeException( "Unable to access execute method: " + command.getClass().getName(), ex );
		}
		catch ( IllegalArgumentException ex ) {
			throw new RuntimeException( "Invalid arguments to execute method: " + command.getClass().getName(), ex );
		}
		catch ( InvocationTargetException ex ) {
			throw new RuntimeException( "Unable to invoke execute method: " + command.getClass().getName(), ex );
		}
	}


}
