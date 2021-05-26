package com.apgsga.testing.sample.db;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SampleDbConfiguration.class)
@AutoConfigureJdbc
public class SampleDbTests {

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonHobbyRepository personHobbyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void setup() {
        genderRepository.deleteAll();
        hobbyRepository.deleteAll();
        personRepository.deleteAll();
        personHobbyRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    void testAddress() {
        addressRepository.saveAll(Lists.newArrayList(
                AddressTable.builder().streetName("Weiherweg")
                        .streetNumber("10")
                        .postalCode("8306")
                        .build(),
                AddressTable.builder().streetName("Hackeracker")
                        .streetNumber("7")
                        .postalCode("8306")
                        .build(),
                AddressTable.builder().streetName("Normalostrasse")
                        .streetNumber("1")
                        .postalCode("8400")
                        .build())
                );
        List<AddressTable> byPostalCode = addressRepository.findByPostalCode("8306");
        assertEquals(2,byPostalCode.size());
        byPostalCode = addressRepository.findByPostalCode("8400");
        assertEquals(1,byPostalCode.size());
        List<AddressTable> hackeracker = addressRepository.findByStreetName("Hackeracker");
        assertEquals(1,hackeracker.size());
        AddressTable weiherweg10 = addressRepository.findByStreetNameAndStreetNumber("Weiherweg", "10");
        assertNotNull(weiherweg10);
        weiherweg10.setStreetName("Weiterweg");
        addressRepository.save(weiherweg10);
        Optional<AddressTable> byId = addressRepository.findById(weiherweg10.getId());
        assertTrue(byId.isPresent());
        assertEquals("Weiterweg", byId.get().getStreetName());

    }

    @Test
    void testGender() {
        GenderTable genderTable = GenderTable.builder().description("Male, Hombre, Mann etc").designation("MALE").build();
        GenderTable result = genderRepository.save(genderTable);
        assertNotNull(result);
        assertNotNull(result.getId());
        Optional<GenderTable> read = genderRepository.findById(result.getId());
        assertTrue(read.isPresent());
        assertEquals(result, read.get());
        genderTable = GenderTable.builder().description("Female, Mujer, Frau etc").designation("FEMALE").build();
        result = genderRepository.save(genderTable);
        assertNotNull(result);
        assertNotNull(result.getId());
        read = genderRepository.findById(result.getId());
        assertTrue(read.isPresent());
        assertEquals(result, read.get());
        Iterable<GenderTable> resultFindAll = genderRepository.findAll();
        Iterator<GenderTable> iterator = resultFindAll.iterator();
        final int[] count = {0};
        iterator.forEachRemaining(genderTable1 -> count[0]++);
        assertEquals(2, count[0]);
    }

    @Test
    void testHobby() {
        HobbyTable football = HobbyTable.builder().shortName("Football").build();
        HobbyTable sports = HobbyTable.builder().shortName("Sports").build();
        HobbyTable art = HobbyTable.builder().shortName("Art").build();
        HobbyTable films = HobbyTable.builder().shortName("Films").build();
        ArrayList<HobbyTable> hobbies = Lists.newArrayList(football, sports, art, films);
        hobbyRepository.saveAll(hobbies);
        Iterable<HobbyTable> all = hobbyRepository.findAll();
        all.forEach(hobbies::remove);
        assertEquals(0, hobbies.size());
        List<HobbyTable> queryResult = hobbyRepository.findByShortName("Art");
        assertEquals(1, queryResult.size());
        queryResult = hobbyRepository.findByShortName("XXXXXX");
        assertEquals(0, queryResult.size());
    }

    @Test
    void testPerson() {
        GenderTable genderTable = GenderTable.builder().description("Male, Hombre, Mann etc").designation("MALE").build();
        genderRepository.save(genderTable);
        PersonTable personTable = PersonTable.builder().firstName("Chris")
                .secondName("Hacks").birthDay(Date.valueOf("1952-06-01"))
                .genderId(genderTable.getId())
                .build();
        PersonTable resultSave = personRepository.save(personTable);
        assertNotNull(resultSave);
        assertNotNull(resultSave.getId());
        Optional<PersonTable> resultFind = personRepository.findById(resultSave.getId());
        assertTrue(resultFind.isPresent());
        assertEquals(resultSave, resultFind.get());
        HobbyTable football = HobbyTable.builder().shortName("Baseball").build();
        HobbyTable sports = HobbyTable.builder().shortName("Sports").build();
        HobbyTable art = HobbyTable.builder().shortName("Art").build();
        HobbyTable films = HobbyTable.builder().shortName("Films").build();
        ArrayList<HobbyTable> hobbies = Lists.newArrayList(football, sports, art, films);
        hobbyRepository.saveAll(hobbies);
        List<HobbyTable> baseballHobbyTable = hobbyRepository.findByShortName("Baseball");
        assertEquals(1, baseballHobbyTable.size());
        personHobbyRepository.save(PersonHobbyTable.builder().
                personId(resultSave.getId()).
                hobbyId(baseballHobbyTable.get(0).id).
                build());
        List<HobbyTable> artHobbyTable = hobbyRepository.findByShortName("Art");
        assertEquals(1, artHobbyTable.size());
        personHobbyRepository.save(PersonHobbyTable.builder().
                personId(resultSave.id).
                hobbyId(artHobbyTable.get(0).id).
                build());
        List<PersonHobbyTable> personsHobbies = personHobbyRepository.findByPersonId(resultSave.getId());
        assertEquals(2, personsHobbies.size());
        AddressTable address = addressRepository.save(AddressTable.builder().streetName("Hackeracker")
                .streetNumber("7")
                .postalCode("8306")
                .build());
        resultSave.setAddressId(address.id);
        personRepository.save(resultSave);
        resultFind = personRepository.findById(resultSave.id);
        assertTrue(resultFind.isPresent());
        assertEquals(resultSave, resultFind.get());
        assertEquals(address.getId(),resultFind.get().addressId);
    }
}
