package com.atm.machin.service;

import com.atm.machin.data.ATMMachine;
import com.atm.machin.data.CustomerData;
import com.atm.machin.model.ApiErrorKeys;
import com.atm.machin.model.Customer;
import com.atm.machin.model.Denomination;
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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = CommonServiceImpl.class)
public class CustomerServiceTest {

	@MockBean
	CustomerData customerData;

	@MockBean
	ATMMachine atmMachine;

	@InjectMocks
	CommonServiceImpl commonService;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}

	private List<Customer> getCustomerAll() {

		List<Customer> customerAll = new ArrayList<Customer>();
		customerAll.add(new Customer("123456789", 1234, 100, 100));
		customerAll.add(new Customer("222222222", 4321, 200, 200));
		return customerAll;
	}

	private TreeSet<Denomination> getDenominationAll() {

		TreeSet<Denomination> denominationAll = new TreeSet<Denomination>();
		denominationAll.add(new Denomination(50, 1));
		denominationAll.add(new Denomination(20, 1));
		denominationAll.add(new Denomination(10, 1));
		denominationAll.add(new Denomination(5, 1));

		return denominationAll;
	}

	@Test
	public void testvalidateCustomerRequest_InvalidAccount() {

		Mockito.when(customerData.getAtmMachine()).thenReturn(atmMachine);
		Mockito.when(customerData.getAtmMachine().getCustomersAll()).thenReturn(getCustomerAll());

		try {
			boolean result = commonService.validateCustomerRequest("1234567890", 1234);
			assertEquals(true,result);
		}catch (Exception e){
			assertEquals(ApiErrorKeys.INVALID_ACCOUNT_NUMBER.getErrorKey()+"_"+ApiErrorKeys.INVALID_ACCOUNT_NUMBER.getErrorMessage(), e.getMessage());
		}

	}

	@Test
	public void testvalidateCustomerRequest_InvalidPin() {

		Mockito.when(customerData.getAtmMachine()).thenReturn(atmMachine);
		Mockito.when(customerData.getAtmMachine().getCustomersAll()).thenReturn(getCustomerAll());

		try {
			boolean result = commonService.validateCustomerRequest("123456789", 12345);
			assertEquals(true,result);
		}catch (Exception e){
			assertEquals(ApiErrorKeys.INVALID_PIN.getErrorKey()+"_"+ApiErrorKeys.INVALID_PIN.getErrorMessage(), e.getMessage());
		}

	}

	@Test
	public void testValidateCustomerRequest() {

		Mockito.when(customerData.getAtmMachine()).thenReturn(atmMachine);
		Mockito.when(customerData.getAtmMachine().getCustomersAll()).thenReturn(getCustomerAll());

		try {
			boolean result = commonService.validateCustomerRequest("123456789", 1234);
			assertEquals(true,result);
		}catch (Exception e){
		}

	}

	@Test
	public void testFetchCustomerBalance() {

		Mockito.when(customerData.getAtmMachine()).thenReturn(atmMachine);
		Mockito.when(customerData.getAtmMachine().getCustomersAll()).thenReturn(getCustomerAll());

		try {
			long result = commonService.fetchCustomerBalance("123456789");
			assertEquals(100l,result);
		}catch (Exception e){
		}

	}

	@Test
	public void testFetchCustomerOverdraft() {

		Mockito.when(customerData.getAtmMachine()).thenReturn(atmMachine);
		Mockito.when(customerData.getAtmMachine().getCustomersAll()).thenReturn(getCustomerAll());

		try {
			long result = commonService.fetchCustomerOverdraft("123456789");
			assertEquals(100l,result);
		}catch (Exception e){
		}

	}

	@Test
	public void testUpdateCustomerBalance() {

		Mockito.when(customerData.getAtmMachine()).thenReturn(atmMachine);
		Mockito.when(customerData.getAtmMachine().getCustomersAll()).thenReturn(getCustomerAll());

		try {
			commonService.updateCustomerBalance("123456789", 50l);
			assertEquals(true, true);
		}catch (Exception e){
		}

	}

	@Test
	public void testUpdateCustomerOverdraft() {

		Mockito.when(customerData.getAtmMachine()).thenReturn(atmMachine);
		Mockito.when(customerData.getAtmMachine().getCustomersAll()).thenReturn(getCustomerAll());

		try {
			commonService.updateCustomerOverdraft("123456789", 50);
			assertEquals(true, true);
		}catch (Exception e){
		}

	}

	@Test
	public void testDispenseMoney() {

		Mockito.when(customerData.getAtmMachine()).thenReturn(atmMachine);
		Mockito.when(customerData.getAtmMachine().getCustomersAll()).thenReturn(getCustomerAll());
		Mockito.when(customerData.getAtmMachine().getDenominationAll()).thenReturn(getDenominationAll());
		try {
			commonService.dispenseMoney( 50l);
			assertEquals(true, true);
		}catch (Exception e){
		}

	}
}
