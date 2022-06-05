package com.atm.machin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@ToString
public class CustomerRequest {

	@JsonProperty("accountNumber")
	private String accountNumber;

	@JsonIgnore
	private int pin;

	@JsonProperty("withDrawAmount")
	private long withDrawAmount;

	public CustomerRequest() {
	}

	public CustomerRequest(String accountNumber, int pin, int withDrawAmount) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.withDrawAmount = withDrawAmount;
	}

}
