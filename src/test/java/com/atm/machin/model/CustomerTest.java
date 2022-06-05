package com.atm.machin.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerTest {

	@Test
	public void testCustomerRequest() {

		Customer customer = new Customer("1234",1234,100,100);
		assertNotNull(customer.toString());
		assertEquals("1234", customer.getAccountNumber());
		assertEquals(1234, customer.getPin());
		assertEquals(100l, customer.getBalanceAmount());
		assertEquals(100, customer.getOverdraftAmount());
	}

}
