package com.atm.machin.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerResultTest {

	@Test
	public void testCustomerResult() {

		CustomerResult customerResult = new CustomerResult();
		customerResult.setAccountNumber("1234");
		customerResult.setPin(1234);
		customerResult.setWithdrawnAmount("333");
		customerResult.setDenominationResult(getDenominationAll());
		customerResult.setBalanceAmount(100l);
		customerResult.setOverdraftAmount(100);
		assertNotNull(customerResult.toString());
		assertEquals("1234", customerResult.getAccountNumber());
		assertEquals(1234, customerResult.getPin());
		assertEquals(100l, customerResult.getBalanceAmount());
		assertEquals(100, customerResult.getOverdraftAmount());
		assertEquals("333", customerResult.getWithdrawnAmount());
		assertEquals(4, customerResult.getDenominationResult().size());
	}

	private List<Denomination> getDenominationAll() {

		List<Denomination> denominationAll = new ArrayList<>();
		denominationAll.add(new Denomination(50, 1));
		denominationAll.add(new Denomination(20, 1));
		denominationAll.add(new Denomination(10, 1));
		denominationAll.add(new Denomination(5, 1));

		return denominationAll;
	}
}
