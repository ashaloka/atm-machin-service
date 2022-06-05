package com.atm.machin.data;

import com.atm.machin.model.Customer;
import com.atm.machin.model.Denomination;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class CustomerData {

	private static List<Customer> customerAll = null;
	
	private static TreeSet<Denomination> denominationAll = null;

	private ATMMachine atmMachine;

	public ATMMachine getAtmMachine() {
		return atmMachine;
	}

	// ATM initialization - With static Data
	@Autowired
	public void setAtmMachine(ATMMachine atmMachine) {

		atmMachine.setCustomersAll(CustomerData.getCustomerAll());
		atmMachine.setDenominationAll(getDenominationAll());

		this.atmMachine = atmMachine;
	}

	/**
	 * Default Static Data set
	 * DB table - customers and load data get it from DB below values using Repository
	 */
	public static List<Customer> getCustomerAll() {
		
		if (customerAll == null) {
			customerAll = new ArrayList<Customer>();
			customerAll.add(new Customer("123456789", 1234, 800, 200));
			customerAll.add(new Customer("987654321", 4321, 1230, 150));
		}
		return customerAll;
	}
	
	/**
	 * Default static Data - Instead of DB - tables script attached but Repo and implementation is pending
	 * DB table - denominations and load data get it from DB below values using Repository
	 */
	public static TreeSet<Denomination> getDenominationAll() {
		
		if (denominationAll == null) {
			denominationAll = new TreeSet<Denomination>();
			denominationAll.add(new Denomination(50, 20));
			denominationAll.add(new Denomination(20, 30));
			denominationAll.add(new Denomination(10, 30));
			denominationAll.add(new Denomination(5, 20));
		}
		return denominationAll;
	}

}
