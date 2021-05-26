package com.apgsga.testing.sample.service;

import com.apgsga.testing.sample.api.*;
import com.apgsga.testing.sample.db.*;
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
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        AddressTable target = mapper.map(address);
        AddressTable result = addressRepository.save(target);
        return mapper.map(result);
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
