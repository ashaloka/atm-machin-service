package com.atm.machin.service;

import com.atm.machin.data.CustomerData;
import com.atm.machin.exceptions.AtmMachinException;
import com.atm.machin.model.ApiErrorKeys;
import com.atm.machin.model.Customer;
import com.atm.machin.model.Denomination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    private CustomerData customerData;

    public CustomerData getCustomerData() {
        return customerData;
    }

    @Autowired
    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }


    @Override
    public boolean validateCustomerRequest(String accountNumber, int pin) {

        Customer customer = findCustomer(accountNumber);
        int resultPin = customer.getPin();
        if(pin == resultPin){
            return true;
        } else {
            throw new AtmMachinException(ApiErrorKeys.INVALID_PIN.getErrorKey() + "_" + ApiErrorKeys.INVALID_PIN.getErrorMessage());
        }

    }

    @Override
    public long fetchCustomerBalance(String accountNumber) {
        Customer customer = findCustomer(accountNumber);
        return customer.getBalanceAmount();

    }

    @Override
    public int fetchCustomerOverdraft(String accountNumber) {
        Customer customer = findCustomer(accountNumber);
        return customer.getOverdraftAmount();
    }

    @Override
    public void updateCustomerBalance(String accountNumber, long customerBalanceNew) {
        Customer customer = findCustomer(accountNumber);
        customer.setBalanceAmount(customerBalanceNew);

    }

    @Override
    public void updateCustomerOverdraft(String accountNumber, int overdraftNew) {
        Customer customer = findCustomer(accountNumber);
        customer.setOverdraftAmount(overdraftNew);
    }

    @Override
    public List<Denomination> dispenseMoney(long withDrawAmount) {
        List<Denomination> denominationResult = new ArrayList<>();
        return calculateWithAllDenomination(Integer.parseInt(String.valueOf(withDrawAmount)), denominationResult);
    }

    /**
     * find Customer with static data or DB
     * @param accountNumber
     * @return
     */
    private Customer findCustomer(String accountNumber) {
        Customer customer = new Customer();
        customer.setAccountNumber(accountNumber);
        List<Customer> customers = getCustomerData().getAtmMachine().getCustomersAll().stream().
                filter(customer1 -> customer1.getAccountNumber().equalsIgnoreCase(accountNumber))
                .collect(Collectors.toList());

        if(customers.isEmpty()){
            throw new AtmMachinException(ApiErrorKeys.INVALID_ACCOUNT_NUMBER.getErrorKey() + "_" +ApiErrorKeys.INVALID_ACCOUNT_NUMBER.getErrorMessage());
        } else {
            return customers.get(0);
        }

    }

    /**
     * Caluclate All Denominations
     * @param amount
     * @param denominationResult
     * @return
     */
    private List<Denomination> calculateWithAllDenomination(int amount, List<Denomination> denominationResult) {

        int remainingAmt = 0;
        //Get Denominations - defined static data and getting instead of this we can get from DB table
        TreeSet<Denomination> denominationAll = getCustomerData().getAtmMachine().getDenominationAll();
        Iterator<Denomination> iterator = denominationAll.iterator();

        Denomination updatedDenomination = null;
        for(Denomination denomination : denominationAll){
            updatedDenomination = denomination;
            if (amount >= denomination.getNoteValue() && denomination.getNoteQuantity() > 0) {
                remainingAmt = calculateWithDenomination(amount, denomination, updatedDenomination, denominationResult);
                break;
            }
        }

        // Update to static DAT - we can update in DB also here
        denominationAll.add(updatedDenomination);
        getCustomerData().getAtmMachine().setDenominationAll(denominationAll);

        // recursion
        if (remainingAmt > 0) {
            calculateWithAllDenomination(remainingAmt, denominationResult);
        }

        return denominationResult;
    }

    /**
     * currentDenomination
     * @param amount
     * @param currentDenomination
     * @param updatedDenomination
     * @param denominationResult
     * @return
     */
    private int calculateWithDenomination(int amount, Denomination currentDenomination, Denomination updatedDenomination, List<Denomination> denominationResult) {

        Denomination denominationToBeGiven = null;

        int remainingAmt = amount % currentDenomination.getNoteValue();
        int noteQuantityRequired = amount / currentDenomination.getNoteValue();
        int noteQuantityToBeGiven = 0;

        if (noteQuantityRequired > currentDenomination.getNoteQuantity()) {
            noteQuantityToBeGiven = currentDenomination.getNoteQuantity();
            remainingAmt = amount - (currentDenomination.getNoteQuantity() * currentDenomination.getNoteValue());
        } else {
            noteQuantityToBeGiven = noteQuantityRequired;
        }

        // Updating NoteQuantity - DB update also we can do here
        updatedDenomination.setNoteQuantity(currentDenomination.getNoteQuantity() - noteQuantityToBeGiven);

        // Updating Result
        denominationToBeGiven = new Denomination(currentDenomination.getNoteValue(), noteQuantityToBeGiven);
        denominationResult.add(denominationToBeGiven);

        return remainingAmt;
    }

    /**
     * Get ATM Balance
     * @return
     */
    public long getATMBalanceAmount() {
        return getCustomerData().getAtmMachine().getBalanceMoney();
    }

    /**
     * Validate WithDraw Amount
     * @param amount
     * @return
     */
    public boolean validateWithdrawalAmount(long amount) {
        int lowestNoteValue = getCustomerData().getAtmMachine().getDenominationAll().last().getNoteValue();
        if (amount > 0 && ((amount % lowestNoteValue) == 0)) {
            return true;
        }
        return false;
    }
}
