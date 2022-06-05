package com.atm.machin.config;

import com.atm.machin.data.ATMMachine;
import com.atm.machin.data.CustomerData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

	@Bean
	public CustomerData customerData() {
		return new CustomerData();
	}

	@Bean
	public ATMMachine atmMachine() { return new ATMMachine();}
	
}
