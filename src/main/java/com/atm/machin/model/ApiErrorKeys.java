package com.atm.machin.model;

import lombok.Getter;

@Getter
public enum ApiErrorKeys {

	ATM_MACHIN_INSUFFICIENT_BALANCE("101", "Insufficient Balance in ATM"),
	ATM_MACHIN_OUT_OF_SERVICE("102", "ATM is out of service"),
	ATM_MACHIN_DEFAULT_ERROR("103","ATM Machin service default Error"),
	BAD_REQUEST("201", "Account Number/Pin is invalida"),
	AUTHENTICATION_ERROR("202", "Authentication Error Invalid PIN/Account Number"),
	INVALID_AMOUNT("203", "Invalid Amount"),
	CUSTOMER_INSUFFICIENT_BALANCE("301", "Insufficient Balance in Customer Account"),
	CUSTOMER_DAILY_LIMIT_WITHDRAWAL_REACHED("302"," Customer Daily with drawal limit reached"),
	INVALID_ACCOUNT_NUMBER("202","Invalid Account Number"),
	INVALID_PIN("202","Invalid PIN"),
	INVALID_ACCOUNT_OR_PIN("202","Invalid AccountNumber/PIN");

	private String errorKey;
	private String errorMessage;

	ApiErrorKeys(String errorKey, String errorMessage){
		this.errorKey = errorKey;
		this.errorMessage = errorMessage;
	}
}
