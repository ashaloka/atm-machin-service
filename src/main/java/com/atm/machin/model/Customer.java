package com.atm.machin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@ToString
public class Customer {

	@JsonProperty("accountNumber")
	private String accountNumber;

	@JsonIgnore
	private int pin;

	@JsonProperty("balanceAmount")
	private long balanceAmount;

	@JsonIgnore
	private int overdraftAmount;

	public Customer() {
	}

	public Customer(String accountNumber, int pin, int balanceAmount, int overdraftAmount) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balanceAmount = balanceAmount;
		this.overdraftAmount = overdraftAmount;
	}

}
