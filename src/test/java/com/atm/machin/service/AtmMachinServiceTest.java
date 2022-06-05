package com.atm.machin.service;

import com.atm.machin.model.ApiErrorKeys;
import com.atm.machin.model.Customer;
import com.atm.machin.model.CustomerRequest;
import com.atm.machin.model.ResponseWrapper;
import com.atm.machin.model.ServiceError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = AtmMachinServiceImpl.class)
public class AtmMachinServiceTest {

	@MockBean
	CommonService commonService;

	@InjectMocks
	AtmMachinServiceImpl atmMachinService;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testgetBalanceEnquiry() {

		CustomerRequest customerRequest = new CustomerRequest("123456789", 1234, 0);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(100l);
		Mockito.when(commonService.validateCustomerRequest(customerRequest.getAccountNumber(), customerRequest.getPin())).thenReturn(true);

		ResponseWrapper responseWrapper = atmMachinService.getBalanceEnquiry(customerRequest);

		assertNotNull(responseWrapper);
		Customer customer = (Customer) responseWrapper.getData();
		assertEquals(customerRequest.getAccountNumber(),customer.getAccountNumber());
	}

	@Test
	public void testgetBalanceEnquiry_InvalidAccountNumber() {

		CustomerRequest customerRequest = new CustomerRequest("1234567890", 1234, 0);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(100l);
		Mockito.when(commonService.validateCustomerRequest(customerRequest.getAccountNumber(), customerRequest.getPin())).thenReturn(true);

		ResponseWrapper responseWrapper = atmMachinService.getBalanceEnquiry(customerRequest);

		assertNotNull(responseWrapper);
		ServiceError serviceError = (ServiceError) responseWrapper.getData();
		assertEquals(ApiErrorKeys.INVALID_ACCOUNT_NUMBER.getErrorKey(), serviceError.getErrorCode());
	}

	@Test
	public void testgetBalanceEnquiry_InvalidPIN() {

		CustomerRequest customerRequest = new CustomerRequest("123456789", 12348, 0);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(100l);
		Mockito.when(commonService.validateCustomerRequest(customerRequest.getAccountNumber(), customerRequest.getPin())).thenReturn(true);

		ResponseWrapper responseWrapper = atmMachinService.getBalanceEnquiry(customerRequest);

		assertNotNull(responseWrapper);
		ServiceError serviceError = (ServiceError) responseWrapper.getData();
		assertEquals(ApiErrorKeys.INVALID_PIN.getErrorKey(), serviceError.getErrorCode());
	}

	@Test
	public void testgetBalanceEnquiry_AuthenticationError() {

		CustomerRequest customerRequest = new CustomerRequest("1234567890", 1236, 0);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(100l);
		Mockito.when(commonService.validateCustomerRequest(customerRequest.getAccountNumber(), customerRequest.getPin())).thenReturn(false);

		ResponseWrapper responseWrapper = atmMachinService.getBalanceEnquiry(customerRequest);

		assertNotNull(responseWrapper);
		ServiceError serviceError = (ServiceError) responseWrapper.getData();
		assertEquals(ApiErrorKeys.AUTHENTICATION_ERROR.getErrorKey(), serviceError.getErrorCode());
	}

	@Test
	public void testWithDrawAmount_InsuffientBalance() {

		CustomerRequest customerRequest = new CustomerRequest("123456789", 1234, 400);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(800l);
		Mockito.when(commonService.validateCustomerRequest(customerRequest.getAccountNumber(), customerRequest.getPin())).thenReturn(true);

		ResponseWrapper responseWrapper = atmMachinService.withDrawAmount(customerRequest);

		assertNotNull(responseWrapper);
		ServiceError serviceError = (ServiceError) responseWrapper.getData();
		assertEquals(ApiErrorKeys.ATM_MACHIN_INSUFFICIENT_BALANCE.getErrorKey(), serviceError.getErrorCode());
	}

	@Test
	public void testWithDrawAmount_Invalid_Amount() {

		CustomerRequest customerRequest = new CustomerRequest("123456789", 1234, 400);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(800l);
		Mockito.when(commonService.validateCustomerRequest(customerRequest.getAccountNumber(), customerRequest.getPin())).thenReturn(true);
		Mockito.when(commonService.getATMBalanceAmount()).thenReturn(1000l);
		ResponseWrapper responseWrapper = atmMachinService.withDrawAmount(customerRequest);

		assertNotNull(responseWrapper);
		ServiceError serviceError = (ServiceError) responseWrapper.getData();
		assertEquals(ApiErrorKeys.INVALID_AMOUNT.getErrorKey(), serviceError.getErrorCode());
	}

	@Test
	public void testWithDrawAmount() {

		CustomerRequest customerRequest = new CustomerRequest("123456789", 1234, 400);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(800l);
		Mockito.when(commonService.validateCustomerRequest(customerRequest.getAccountNumber(), customerRequest.getPin())).thenReturn(true);
		Mockito.when(commonService.getATMBalanceAmount()).thenReturn(1000l);
		Mockito.when(commonService.validateWithdrawalAmount(customerRequest.getWithDrawAmount())).thenReturn(true);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(800l);
		Mockito.when(commonService.fetchCustomerOverdraft(customerRequest.getAccountNumber())).thenReturn(200);
		ResponseWrapper responseWrapper = atmMachinService.withDrawAmount(customerRequest);

		assertNotNull(responseWrapper);
		Customer customer = (Customer) responseWrapper.getData();
		assertEquals(customerRequest.getAccountNumber(),customer.getAccountNumber());
	}

	@Test
	public void testWithDrawAmount_WithDrawGreaterThanOverDraft() {

		CustomerRequest customerRequest = new CustomerRequest("123456789", 1234, 400);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(1000l);
		Mockito.when(commonService.validateCustomerRequest(customerRequest.getAccountNumber(), customerRequest.getPin())).thenReturn(true);
		Mockito.when(commonService.getATMBalanceAmount()).thenReturn(1000l);
		Mockito.when(commonService.validateWithdrawalAmount(customerRequest.getWithDrawAmount())).thenReturn(true);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(1000l);
		Mockito.when(commonService.fetchCustomerOverdraft(customerRequest.getAccountNumber())).thenReturn(200);
		ResponseWrapper responseWrapper = atmMachinService.withDrawAmount(customerRequest);

		assertNotNull(responseWrapper);
		Customer customer = (Customer) responseWrapper.getData();
		assertEquals(customerRequest.getAccountNumber(),customer.getAccountNumber());
	}

	@Test
	public void testWithDrawAmount_CustomerInsufficientBalance() {

		CustomerRequest customerRequest = new CustomerRequest("123456789", 1234, 400);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(100l);
		Mockito.when(commonService.validateCustomerRequest(customerRequest.getAccountNumber(), customerRequest.getPin())).thenReturn(true);
		Mockito.when(commonService.getATMBalanceAmount()).thenReturn(1000l);
		Mockito.when(commonService.validateWithdrawalAmount(customerRequest.getWithDrawAmount())).thenReturn(true);
		Mockito.when(commonService.fetchCustomerBalance(customerRequest.getAccountNumber())).thenReturn(100l);
		Mockito.when(commonService.fetchCustomerOverdraft(customerRequest.getAccountNumber())).thenReturn(200);
		ResponseWrapper responseWrapper = atmMachinService.withDrawAmount(customerRequest);

		assertNotNull(responseWrapper);
		ServiceError serviceError = (ServiceError) responseWrapper.getData();
		assertEquals(ApiErrorKeys.CUSTOMER_INSUFFICIENT_BALANCE.getErrorKey(), serviceError.getErrorCode());
	}
}
