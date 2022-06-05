package com.atm.machin.service;

import com.atm.machin.model.Denomination;

import java.util.List;

public interface CommonService {

    boolean validateCustomerRequest(String accountNumber, int pin);

    long fetchCustomerBalance(String accountNumber);

    int fetchCustomerOverdraft(String accountNumber);

    void updateCustomerBalance(String accountNumber, long customerBalanceNew);

    void updateCustomerOverdraft(String accountNumber, int overdraftNew);

    List<Denomination> dispenseMoney(long withDrawAmount);

    long getATMBalanceAmount();

    boolean validateWithdrawalAmount(long amount);
}
