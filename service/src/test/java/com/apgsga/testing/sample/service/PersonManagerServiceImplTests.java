package com.apgsga.testing.sample.service;

import com.apgsga.testing.sample.api.Address;
import com.apgsga.testing.sample.api.PersonManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PersonManagerServiceImplTests {

    @Autowired
    PersonManagerService service;

    @BeforeEach
    void setup() {
        service.deleteAll();
    }

    @Test
    public void contextLoad() {

    }

    @Test
    public void addressTests() {
        Address address = Address.builder().setStreetName("Hackerswil")
                .setStreetNumber("007")
                .setPostalCode("8306").build();
        Address result = service.create(address);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(address.getPostalCode(),result.getPostalCode());
        assertEquals(address.getStreetName(),result.getStreetName());
        assertEquals(address.getStreetNumber(),result.getStreetNumber());
    }


}
