package com.atm.machin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CustomerResult extends Customer {

	@JsonProperty("withdrawnAmount")
	private String withdrawnAmount;

	@JsonProperty("withdrawnNotes")
	private List<Denomination> denominationResult;

	public List<Denomination> getDenominationResult() {
		return denominationResult;
	}

	public void setDenominationResult(List<Denomination> denominationResult) {
		this.denominationResult = denominationResult;
	}

	public String getWithdrawnAmount() {
		return withdrawnAmount;
	}

	public void setWithdrawnAmount(String withdrawnAmount) {
		this.withdrawnAmount = withdrawnAmount;
	}
	
}
