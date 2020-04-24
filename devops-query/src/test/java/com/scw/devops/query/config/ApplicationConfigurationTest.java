package com.scw.devops.query.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.core.MethodParameter;

public class ApplicationConfigurationTest {

	@Test
	public void logger() {
		Class clazz = String.class;

		MethodParameter mockMethodParameter = mock(MethodParameter.class);
		when(mockMethodParameter.getContainingClass()).thenReturn(clazz);

		InjectionPoint mockInjectionPoint = mock(InjectionPoint.class);
		when(mockInjectionPoint.getMethodParameter()).thenReturn(mockMethodParameter);

		// TODO assertThat(new ApplicationConfiguration(null).logger(mockInjectionPoint), notNullValue());
	}
}