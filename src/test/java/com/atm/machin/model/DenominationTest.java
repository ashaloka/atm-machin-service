package com.atm.machin.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DenominationTest {

	@Test
	public void testDenomination() {

		Denomination denomination = new Denomination(100,20);
		denomination.setNoteQuantity(20);
		denomination.setNoteValue(100);
		assertNotNull(denomination.toString());
		assertEquals(20, denomination.getNoteQuantity());
		assertEquals(100, denomination.getNoteValue());
	}

}
