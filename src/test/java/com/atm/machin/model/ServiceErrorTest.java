package com.atm.machin.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ServiceErrorTest {

	@Test
	public void testServiceError() {

		ServiceError serviceError = new ServiceError("1234","Error");
		serviceError.setErrorCode("1234");
		serviceError.setErrorMessage("Error");
		assertNotNull(serviceError.toString());
		assertEquals("1234", serviceError.getErrorCode());
		assertEquals("Error", serviceError.getErrorMessage());
	}

}
