package com.apgsga.testing.sample.api;

import java.util.List;

public interface PersonManagerService {
    void deleteAll(); // Testing purposes
    Address create(Address address);
    Gender create(Gender gender);
    Person create(Person person);
    Hobby create(Hobby hobby);
    List<Hobby> create(List<Hobby> hobbies);
    Address update(Address address);
    Gender update(Gender gender);
    Person update(Person person);
    Hobby update(Hobby hobby);
    Person findById(Integer personId);
    PersonHobbies addHobby(Person person, Hobby hobby);
    Person updatePersonAddress(Person person, Address address);
    PersonHobbies listHobbies(Integer personId);
    PersonAddress listAddress(Integer personId);
    List<Hobby> listHobbies();
    List<Gender> listGenders();
    List<Address> listAddresses();
}
