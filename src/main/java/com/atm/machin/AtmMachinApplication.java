package com.atm.machin;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@ComponentScan("com.atm.machin")
@EnableAutoConfiguration
@EnableEncryptableProperties
@Slf4j
public class AtmMachinApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.atm.machin.AtmMachinApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(com.atm.machin.AtmMachinApplication.class, args);

        log.info("\n\n----------------------------------------------------------------------\n\t" +
                " ATM Machin Application Started Successfully " +
                "\n--------------------------------------------------------------------------\n");
    }

}
