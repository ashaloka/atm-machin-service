package com.atm.machin.controller;

import com.atm.machin.model.CustomerRequest;
import com.atm.machin.model.ResponseWrapper;
import com.atm.machin.service.AtmMachinService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm-machin")
@Slf4j
public class AtmMachinController {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.atm.machin.controller.AtmMachinController.class);

    @Autowired
    AtmMachinService atmMachinService;

    @RequestMapping(path = "/heartbeat",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHealthDetails(){

        return ResponseEntity.status(HttpStatus.OK).body("{\"Status\": \"Ok\"}");

    }

    @RequestMapping(path = "/balance-enquiry",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> getBalanceEnquiry(
            @RequestParam(name="accountNumber") String accountNumber,
            @RequestParam(name="pin") String pin) {

        log.info("Entering In getBalanceEnquiry() for {} ", accountNumber);

        CustomerRequest customerRequest = new CustomerRequest(accountNumber, Integer.parseInt(pin), 0);

        ResponseWrapper responseWrapper = atmMachinService.getBalanceEnquiry(customerRequest);

        return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
    }

    @RequestMapping(path = "/with-draw-amount",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> withDrawAmount(
            @RequestParam(name="accountNumber") String accountNumber,
            @RequestParam(name="pin") String pin,
            @RequestParam(name="requestAmount") int requestAmount) {

        log.info("In withDrawAmount() for AccountNumber, RequestAmount: {} {} ",accountNumber,requestAmount);

        CustomerRequest customerRequest = new CustomerRequest(accountNumber, Integer.parseInt(pin), requestAmount);

        ResponseWrapper responseWrapper = atmMachinService.withDrawAmount(customerRequest);

        log.info("Completed WithDraw Amount Process for Account : {}", accountNumber);

        return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
    }

}
