package com.atm.machin.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerRequestTest {

	@Test
	public void testCustomerRequest() {

		CustomerRequest customerRequest = new CustomerRequest("1234",1234,100);
		assertNotNull(customerRequest.toString());
		assertEquals("1234", customerRequest.getAccountNumber());
		assertEquals(1234, customerRequest.getPin());
		assertEquals(100l, customerRequest.getWithDrawAmount());
	}

}
