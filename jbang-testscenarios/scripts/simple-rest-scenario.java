///usr/bin/env jbang "$0" "$@" ; exit $?
//REPOS local=file:///Users/chhex/maven/testrepo
//DEPS com.apgsga.testing.sample:api-testing-sample-client:0.3.6-SNAPSHOT
//DEPS org.junit.jupiter:junit-jupiter-api:5.5.2
//FILES logback.xml ./logback.xml

import com.apgsga.testing.sample.client.rest.RestClient;
import com.apgsga.testing.sample.api.Address;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Assertions;


class simpletestscenario {


    public static void main(String[] args) {
        Logger LOGGER = LoggerFactory.getLogger("Simple Testscenario");
        LOGGER.info("Starting Test Scenario");
        RestClient client = new RestClient("http://localhost:8080");
        LOGGER.info("***Setting up Test Scenario ");
        // Test Scenario Setup Code
        client.deleteAll();
        LOGGER.info("Deleted all Test Data");
        LOGGER.info("***Setup Done.");
        LOGGER.info("***Test Scenario.");
        LOGGER.info("Creating first Address ....");
        Address hackerswyler = client.create(Address.builder()
                .streetName("Hackerswyler")
                .streetNumber("007")
                .postalCode("8306")
                .build());
        LOGGER.info(String.format("Created : %s", hackerswyler.toString()));
        LOGGER.info("Creating second Address ....");
        Address widacker = client.create(Address.builder()
                .streetName("Widacker")
                .streetNumber("1")
                .postalCode("9000")
                .build());
        LOGGER.info(String.format("Created : %s", widacker.toString()));
        LOGGER.info("Listing and asserting Addresses : ");
        List<Address> addresses = client.listAddresses();
        for (Address address : addresses) {
            LOGGER.info(address.toString());
        }
        Assertions.assertEquals(hackerswyler.getPostalCode(), addresses.get(0).getPostalCode());
        Assertions.assertEquals(hackerswyler.getStreetName(), addresses.get(0).getStreetName());
        Assertions.assertEquals(hackerswyler.getStreetNumber(), addresses.get(0).getStreetNumber());
        Assertions.assertEquals(widacker.getPostalCode(), addresses.get(1).getPostalCode());
        Assertions.assertEquals(widacker.getStreetName(), addresses.get(1).getStreetName());
        Assertions.assertEquals(widacker.getStreetNumber(), addresses.get(1).getStreetNumber());
        LOGGER.info("***Test Senario Done and gone.");
    }
}