package com.atm.machin.service;

import com.atm.machin.model.CustomerRequest;
import com.atm.machin.model.ResponseWrapper;

public interface AtmMachinService {

    ResponseWrapper getBalanceEnquiry(CustomerRequest customerRequest);

    ResponseWrapper withDrawAmount(CustomerRequest customerRequest);
}
