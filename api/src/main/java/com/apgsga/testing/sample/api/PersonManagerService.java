package com.apgsga.testing.sample.api;

import java.util.List;

public interface PersonManagerService {
    void deleteAll(); // Testing purposes
    Address create(Address address);
    Gender create(Gender gender);
    Person create(Person person);
    Hobby create(Hobby hobby);
    Address update(Address address);
    Gender update(Gender gender);
    Person update(Person person);
    Hobby update(Hobby hobby);
    void addHobby(Person person, Hobby hobby);
    void setAddress(Person person, Address address);
    List<PersonHobbies> listHobbies(Long personId);
    PersonAddress listAddress(Long personId);
    List<Hobby> listHobbies();
    List<Gender> listGenders();
    List<Address> listAddresses();

}
