package com.atm.machin.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseWrapperTest {

	@Test
	public void testResponseWrapper() {

		Customer customer = new Customer("1234", 1234, 100, 100);
		ResponseWrapper responseWrapper = new ResponseWrapper(customer);

		Customer result = (Customer) responseWrapper.getData();
		assertNotNull(responseWrapper.toString());
		assertEquals("1234", result.getAccountNumber());
		assertEquals(1234, result.getPin());
		assertEquals(100l, result.getBalanceAmount());
	}

}
