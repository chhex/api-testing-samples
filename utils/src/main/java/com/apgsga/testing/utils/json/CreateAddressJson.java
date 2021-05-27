package com.apgsga.testing.utils.json;

import com.apgsga.testing.sample.api.Address;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class CreateAddressJson {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("ExampleAddress.json"),
                Address.builder()
                        .postalCode("8306")
                        .streetName("Hackersdum")
                        .streetNumber("007").build());
    }
}
