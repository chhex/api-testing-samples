package com.apgsga.testing.sample.service;

import com.apgsga.testing.sample.api.*;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void testAddress() {
        Address address = Address.builder().streetName("Hackerswil")
                .streetNumber("007")
                .postalCode("8306").build();
        Address result = service.create(address);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(address.getPostalCode(),result.getPostalCode());
        assertEquals(address.getStreetName(),result.getStreetName());
        assertEquals(address.getStreetNumber(),result.getStreetNumber());
    }

    @Test
    void testGender() {
        Gender gender = Gender.builder().description("Male, Hombre, Mann etc").designation("MALE").build();
        Gender result = service.create(gender);
        assertNotNull(result);
        assertNotNull(result.getId());
        List<Gender> genders = service.listGenders();
        assertEquals(1,genders.size());
        assertNotNull(genders.get(0).getId());
        gender = Gender.builder().description("Female, Mujer, Frau").designation("FEMALE").build();
        result = service.create(gender);
        assertNotNull(result);
        assertNotNull(result.getId());
        genders = service.listGenders();
        assertEquals(2,genders.size());
    }

    @Test
    void testHobby() {
        Hobby football = Hobby.builder().shortName("Football").build();
        Hobby sports = Hobby.builder().shortName("Sports").build();
        Hobby art = Hobby.builder().shortName("Art").build();
        Hobby films = Hobby.builder().shortName("Films").build();
        Hobby footballCreated = service.create(football);
        assertNotNull(footballCreated);
        assertNotNull(footballCreated.getId());
        Hobby sportsCreated = service.create(sports);
        assertNotNull(sportsCreated);
        assertNotNull(sportsCreated.getId());
        Hobby artCreated = service.create(art);
        assertNotNull(artCreated);
        assertNotNull(artCreated.getId());
        Hobby filmsCreated = service.create(films);
        assertNotNull(filmsCreated);
        assertNotNull(filmsCreated.getId());
        List<Hobby> all = service.listHobbies();
        assertEquals(4,all.size());
        List<Hobby> hobbies = Lists.newArrayList(footballCreated, sportsCreated, artCreated, filmsCreated);
        all.forEach(hobbies::remove);
        assertEquals(0, hobbies.size());
    }

    @Test
    void testPerson() {
        Gender gender = Gender.builder().description("Male, Hombre, Mann etc").designation("MALE").build();
        Gender genderResult = service.create(gender);
        Person person = Person.builder().firstName("Chris")
                .secondName("Hacks").birthDay(Date.valueOf("1952-06-01"))
                .gender(genderResult)
                .build();
        Person resultSave = service.create(person);
        assertNotNull(resultSave);
        assertNotNull(resultSave.getId());
        Person resultFind = service.findById(resultSave.getId());
        assertNotNull(resultFind);
        assertEquals(resultSave, resultFind);
        Hobby baseball = Hobby.builder().shortName("Baseball").build();
        Hobby sports = Hobby.builder().shortName("Sports").build();
        Hobby art = Hobby.builder().shortName("Art").build();
        Hobby films = Hobby.builder().shortName("Films").build();
        List<Hobby> hobbies = service.create(Lists.newArrayList(baseball, sports, art, films));
        assertEquals(4,hobbies.size());
        assertEquals("Baseball",hobbies.get(0).getShortName());
        assertEquals("Sports",hobbies.get(1).getShortName());
        assertEquals("Art",hobbies.get(2).getShortName());
        assertEquals("Films",hobbies.get(3).getShortName());
        PersonHobbies personHobbies = service.addHobby(resultFind, hobbies.get(0));
        assertNotNull(personHobbies);
        assertEquals(resultFind.getId(), personHobbies.getPerson().getId());
        assertEquals("Chris", personHobbies.getPerson().getFirstName());
        assertEquals("Hacks", personHobbies.getPerson().getSecondName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("1952-06-01", simpleDateFormat.format(personHobbies.getPerson().getBirthDay()));
        assertEquals(genderResult.getId(), personHobbies.getPerson().getGender().getId());
        assertEquals("MALE", personHobbies.getPerson().getGender().getDesignation());
        assertEquals("Male, Hombre, Mann etc", personHobbies.getPerson().getGender().getDescription());
        assertEquals(1, personHobbies.getHobbies().size());
        assertEquals("Baseball", personHobbies.getHobbies().get(0).getShortName());
        assertNotNull(personHobbies.getHobbies().get(0).getId());
        Address address = service.create(Address.builder()
                .streetName("Hackeracker")
                .streetNumber("7")
                .postalCode("8306")
                .build());
        Person updateResult = service.updatePersonAddress(resultFind, address);
        assertEquals(resultFind.getId(), updateResult.getId());
        assertEquals("Chris", updateResult.getFirstName());
        assertEquals("Hacks", updateResult.getSecondName());
        assertEquals("1952-06-01", simpleDateFormat.format(updateResult.getBirthDay()));
        assertEquals(genderResult.getId(), updateResult.getGender().getId());
        assertEquals("MALE", updateResult.getGender().getDesignation());
        assertEquals("Male, Hombre, Mann etc", updateResult.getGender().getDescription());
        assertNotNull(updateResult.getAddress());
        assertEquals("Hackeracker", updateResult.getAddress().getStreetName());
        assertEquals("7", updateResult.getAddress().getStreetNumber());
        assertEquals(address.getId(), updateResult.getAddress().getId());
    }


}
