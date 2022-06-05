package com.atm.machin.controller;


import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.Assert.assertEquals;

public class AtmMachinControllerTest extends AbstractTest{

	@Override
	@Before
	public void setUp(){
		super.setUp();
	}

	@Test
	public void testBalanceEnquiry() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/atm-machin/balance-enquiry?accountNumber=123456789&pin=1234")
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);


	}


	@Test
	public void testHeartBeat() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/atm-machin/heartbeat")
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);


	}

	@Test
	public void testWithDraw() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/atm-machin/with-draw-amount?accountNumber=123456789&pin=1236&requestAmount=250")
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);


	}

}
