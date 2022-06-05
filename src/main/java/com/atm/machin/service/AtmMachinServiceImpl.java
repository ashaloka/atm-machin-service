package com.atm.machin.service;

import com.atm.machin.constants.Constants;
import com.atm.machin.exceptions.AtmMachinException;
import com.atm.machin.model.ApiErrorKeys;
import com.atm.machin.model.Customer;
import com.atm.machin.model.CustomerRequest;
import com.atm.machin.model.CustomerResult;
import com.atm.machin.model.Denomination;
import com.atm.machin.model.ResponseWrapper;
import com.atm.machin.model.ServiceError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AtmMachinServiceImpl implements AtmMachinService {

    @Autowired
    CommonService commonService;

    @Override
    public ResponseWrapper getBalanceEnquiry(CustomerRequest customerRequest) {

        log.debug("Entering In getBalanceEnquiry method()");
        ResponseWrapper responseWrapper = null;

        try {
            boolean isPinValid = validateCustomerAccountNumberAndPin(customerRequest.getAccountNumber(), customerRequest.getPin());
            log.info("Pin validated successfully for validated, account : {} {}", isPinValid, customerRequest.getAccountNumber());
            responseWrapper = getCustomerBalance(customerRequest.getAccountNumber());

        } catch (Exception e) {
            log.error("Exception in getBalanceEnquiry : {}", e);

            responseWrapper = prepareErrorResponse(e.getMessage());
        }
        log.debug("Ending from getBalanceEnquiry method");
        return responseWrapper;
    }

    @Override
    public ResponseWrapper withDrawAmount(CustomerRequest customerRequest) {

        log.debug("Entering In withDrawAmount");
        ResponseWrapper responseWrapper = null;

        try {
            boolean isPinValid = validateCustomerAccountNumberAndPin(customerRequest.getAccountNumber(), customerRequest.getPin());
            log.info("Pin validated successfully for Validated,account : {} {}", isPinValid, customerRequest.getAccountNumber());

            responseWrapper = withDrawMoney(customerRequest.getAccountNumber(),
                    customerRequest.getWithDrawAmount());

        } catch (Exception e) {
           log.error("Exception in WithDraAmount : {}", e);
            responseWrapper = prepareErrorResponse(e.getMessage());
        }

        log.debug("Completed withDraw Process");

        return responseWrapper;
    }

    /**
     * With Draw Money
     * @param accountNumber
     * @param withDrawAmount
     * @return
     */
    private ResponseWrapper withDrawMoney(String accountNumber, long withDrawAmount) {
        log.debug("Entering in withDrawMoney method :{}", "");

        ResponseWrapper responseWrapper = null;

        //Validate ATM MAchin Balance
        validateATMMachinBalance(withDrawAmount);

        //Validate Customer Amount With Withdraw balance
        validateCustomerWithDrawAmountATMMachinBalance(withDrawAmount);

        //validate customer Amount
        CustomerResult customerResult = validateCustomerAmount(accountNumber, withDrawAmount);

        return new ResponseWrapper(customerResult);
    }

    /**
     * Validate Customer Amount
     * @param accountNumber
     * @param withDrawAmount
     * @return
     */
    private CustomerResult validateCustomerAmount(String accountNumber, long withDrawAmount) {
        int overDraftCurrent = 0;
        long customerBalanceNew = 0;
        int overdraftNew = 0;
        long customerBalance = commonService.fetchCustomerBalance(accountNumber);
        log.info("Customer Balance : {}", customerBalance);

        if (withDrawAmount > customerBalance) {
            overDraftCurrent = commonService.fetchCustomerOverdraft(accountNumber);
            log.info("Customer OverDraft Limit : {}", overDraftCurrent);

            if(withDrawAmount > (customerBalance + overDraftCurrent)) {
                throw new AtmMachinException(ApiErrorKeys.CUSTOMER_INSUFFICIENT_BALANCE.getErrorKey() + "_" +ApiErrorKeys.CUSTOMER_INSUFFICIENT_BALANCE.getErrorMessage());
            }
            overdraftNew = (int) (overDraftCurrent - (withDrawAmount - customerBalance));
            customerBalanceNew = 0;

        } else {
            customerBalanceNew = customerBalance - withDrawAmount;
            overdraftNew = overDraftCurrent;
        }

        updateCustomerBalance(accountNumber, customerBalanceNew, overdraftNew, overDraftCurrent);

        return updateCustomerResult(withDrawAmount, customerBalanceNew, accountNumber, withDrawAmount);

    }

    /**
     * Update Customer Amount and Denomination to static block
     * Instead of this update DB also here
     * @param withDrawAmount
     * @param customerBalanceNew
     * @param accountNumber
     * @param withDrawAmount1
     * @return
     */
    private CustomerResult updateCustomerResult(long withDrawAmount, long customerBalanceNew, String accountNumber, long withDrawAmount1) {
        List<Denomination> denominationResult = commonService.dispenseMoney(withDrawAmount);

        CustomerResult customerResult = new CustomerResult();
        customerResult.setAccountNumber(accountNumber);
        customerResult.setBalanceAmount(customerBalanceNew);
        customerResult.setWithdrawnAmount(withDrawAmount+"");
        customerResult.setDenominationResult(denominationResult);

        return customerResult;
    }

    /**
     * Update Balance Amount
     * @param accountNumber
     * @param customerBalanceNew
     * @param overdraftNew
     * @param overDraftCurrent
     */
    private void updateCustomerBalance(String accountNumber, long customerBalanceNew, int overdraftNew, int overDraftCurrent) {
        try {
            commonService.updateCustomerBalance(accountNumber, customerBalanceNew);
            if (overDraftCurrent != overdraftNew) {
                commonService.updateCustomerOverdraft(accountNumber, overdraftNew);
            }
        } catch (Exception e) {
            log.error("Exception in withdraw amount :{}", e);
            throw new AtmMachinException(ApiErrorKeys.ATM_MACHIN_DEFAULT_ERROR.getErrorKey() + "_" +ApiErrorKeys.ATM_MACHIN_DEFAULT_ERROR.getErrorMessage());
        }
    }

    /**
     * Validate Customer withdraw amount with ATMMachin Balance
     * @param withDrawAmount
     */
    private void validateCustomerWithDrawAmountATMMachinBalance(long withDrawAmount) {
        boolean isRequestedAmountValid = commonService.validateWithdrawalAmount(withDrawAmount);
        if (!isRequestedAmountValid) {
            throw new AtmMachinException(ApiErrorKeys.INVALID_AMOUNT.getErrorKey() + "_" + ApiErrorKeys.INVALID_AMOUNT.getErrorMessage());
        }
    }

    /**
     * Validate ATM Machin balance
     * @param withDrawAmount
     */
    private void validateATMMachinBalance(long withDrawAmount) {
        long atmMachinBalance = commonService.getATMBalanceAmount();
        log.debug("ATM Balance : {}", atmMachinBalance);
        if (atmMachinBalance == 0) {
            throw new AtmMachinException(ApiErrorKeys.ATM_MACHIN_INSUFFICIENT_BALANCE.getErrorKey() + "_" +ApiErrorKeys.ATM_MACHIN_INSUFFICIENT_BALANCE.getErrorMessage());
        }

        //withdraw amount is > ATM Balance
        if (withDrawAmount > atmMachinBalance) {
            throw new AtmMachinException(ApiErrorKeys.ATM_MACHIN_INSUFFICIENT_BALANCE.getErrorKey() +"_" +ApiErrorKeys.ATM_MACHIN_INSUFFICIENT_BALANCE.getErrorMessage());
        }
    }

    /**
     * Service Error
     * @param errorCode
     * @return
     */
    private ResponseWrapper<ServiceError> prepareErrorResponse(String errorCode) {
        ServiceError serviceError = null;

        if(errorCode != null) {
            if (errorCode.contains("_")) {
                String[] errorDetails = errorCode.split("_");
                serviceError = new ServiceError(errorDetails[0], errorDetails[1]);
            } else {
                serviceError = new ServiceError(errorCode, "ATM Machin is not working");
            }
        } else {
            new ServiceError("202", errorCode);
        }
        return new ResponseWrapper<>(serviceError);
    }

    /**
     * Get Customer Balance
     * @param accountNumber
     * @return
     */
    private ResponseWrapper getCustomerBalance(String accountNumber) {

        long balance = commonService.fetchCustomerBalance(accountNumber);

        Customer customerResult = new Customer();
        customerResult.setAccountNumber(accountNumber);
        customerResult.setBalanceAmount(balance);

        return new ResponseWrapper<>(customerResult);
    }

    /**
     * Validate Customer Account Number And PIN
     * @param accountNumber
     * @param pin
     * @return
     */
    private boolean validateCustomerAccountNumberAndPin(String accountNumber, int pin) {
        boolean isPinValid = false;
        if (validateAccountNumber(accountNumber) && validatePin(pin)) {
            isPinValid = commonService.validateCustomerRequest(accountNumber, pin);
            if(!isPinValid) {
                throw new AtmMachinException(ApiErrorKeys.AUTHENTICATION_ERROR.getErrorKey()+"_"+ApiErrorKeys.AUTHENTICATION_ERROR.getErrorMessage());
            }
        }
        return isPinValid;
    }

    /**
     * Validate Account Number
     * @param accountNumber
     * @return
     */
    private boolean validateAccountNumber(String accountNumber) {
        if (accountNumber.length() == Constants.CUSTOMER_ACCOUNT_NUMBER_LENGTH) {
            return true;
        } else {
            throw new AtmMachinException(ApiErrorKeys.INVALID_ACCOUNT_NUMBER.getErrorKey()+"_"+ApiErrorKeys.INVALID_ACCOUNT_NUMBER.getErrorMessage());
        }
    }

    /**
     * Validate PIN
     * @param pin
     * @return
     */
    private boolean validatePin(int pin) {
        String pinStr = pin + "";
        if (pinStr.length() == Constants.CUSTOMER_ACCOUNT_PIN_LENGTH) {
            return true;
        }
        else{
            throw new AtmMachinException(ApiErrorKeys.INVALID_PIN.getErrorKey()+"_"+ApiErrorKeys.INVALID_PIN.getErrorMessage());
        }
    }

}
