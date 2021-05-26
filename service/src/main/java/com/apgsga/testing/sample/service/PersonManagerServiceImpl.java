package com.apgsga.testing.sample.service;

import com.apgsga.testing.sample.api.*;
import com.apgsga.testing.sample.api.Address;
import com.apgsga.testing.sample.api.Gender;
import com.apgsga.testing.sample.api.Hobby;
import com.apgsga.testing.sample.api.Person;
import com.apgsga.testing.sample.db.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonManagerServiceImpl implements PersonManagerService {


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


    @Override
    public void deleteAll() {
        genderRepository.deleteAll();
        hobbyRepository.deleteAll();
        personRepository.deleteAll();
        personHobbyRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Override
    public Address create(Address address) {
        // TODO (CHE, 26.5) : Add some Business Rules
        AddressTable target = AddressTable.builder().build();
        BeanUtils.copyProperties(address, target);
        AddressTable result = addressRepository.save(target);
        final Address.AddressBuilder addressBuilder = Address.builder();
        BeanUtils.copyProperties(result,addressBuilder);
        return addressBuilder.build();
    }

    @Override
    public Gender create(Gender gender)  {
        return null;
    }

    @Override
    public Person create(Person person) {
        return null;
    }

    @Override
    public Hobby create(Hobby hobby) {
        return null;
    }

    @Override
    public Address update(Address address) {
        return null;
    }

    @Override
    public Gender update(Gender gender) {
        return null;
    }

    @Override
    public Person update(Person person) {
        return null;
    }

    @Override
    public Hobby update(Hobby hobby) {
        return null;
    }

    @Override
    public void addHobby(Person person, Hobby hobby) {

    }

    @Override
    public void setAddress(Person person, Address address) {

    }

    @Override
    public List<PersonHobbies> listHobbies(Long personId) {
        return null;
    }

    @Override
    public PersonAddress listAddress(Long personId) {
        return null;
    }

    @Override
    public List<Hobby> listHobbies() {
        return null;
    }

    @Override
    public List<Gender> listGenders() {
        return null;
    }

    @Override
    public List<Address> listAddresses() {
        return null;
    }
}
