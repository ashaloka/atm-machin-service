package com.atm.machin.data;

import com.atm.machin.model.Customer;
import com.atm.machin.model.Denomination;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class ATMMachine {

	private TreeSet<Denomination> denominationAll;
	
	private List<Customer> customersAll;

	public TreeSet<Denomination> getDenominationAll() {
		return denominationAll;
	}

	public void setDenominationAll(TreeSet<Denomination> denominationAll) {
		this.denominationAll = denominationAll;
	}

	public List<Customer> getCustomersAll() {
		return customersAll;
	}

	public void setCustomersAll(List<Customer> customersAll) {
		this.customersAll = customersAll;
	}
	
	public long getBalanceMoney() {
		
		long balanceMoney = 0;
		Iterator<Denomination> iterator = this.denominationAll.iterator();
		
		while (iterator.hasNext()) {
			Denomination d = iterator.next();
			balanceMoney += (d.getNoteValue() * d.getNoteQuantity()); 
		}
		
		return balanceMoney;
	}

}
