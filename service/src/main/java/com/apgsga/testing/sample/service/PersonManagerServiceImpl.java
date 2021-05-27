package com.apgsga.testing.sample.service;

import com.apgsga.testing.sample.api.*;
import com.apgsga.testing.sample.db.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// TODO (che, 27.6) : Mapper as Spring Bean, see https://github.com/mapstruct/mapstruct-spring-extensions

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
        assert address.getId() == null;
        // TODO (CHE, 26.5) : Example Business Rules
        return save(address);
    }


    @Override
    public Gender create(Gender gender)  {
        assert gender.getId() == null;
        // TODO (CHE, 27.5) : Example Business Rules
        return save(gender);
    }

    @Override
    public Person create(Person person) {
        assert person.getId() == null;
        // Business Rules
        return save(person);
    }

    @Override
    public Hobby create(Hobby hobby) {
        assert hobby.getId() == null;
        // Business Rules
        return save(hobby);
    }

    @Override
    public List<Hobby> create(List<Hobby> hobbies) {
        List<Hobby> resultList = Lists.newArrayList();
        for (Hobby hobby : hobbies) {
            resultList.add(create(hobby));
        }
        return resultList;
    }


    @Override
    public Address update(Address address) {
        assert address.getId() != null;
        // Business Rules
        return save(address);
    }

    @Override
    public Gender update(Gender gender) {
        assert gender.getId() != null;
        // Business Rules
        return save(gender);
    }

    @Override
    public Person update(Person person) {
        assert person.getId() != null;
        // Business Rules
        return save(person);
    }

    @Override
    public Hobby update(Hobby hobby) {
        assert hobby.getId() != null;
        // Business Rules
        return save(hobby);
    }

    @Override
    public Person findById(Integer personId) {
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        Optional<PersonTable> result = personRepository.findById(personId);
        if (result.isPresent()) {
            return map(result.get());
        }
        return null;
    }

    @Override
    public PersonHobbies addHobby(Person person, Hobby hobby) {
        assert hobby.getId() != null;
        assert person.getId() != null;
        //  Business Rules
        personHobbyRepository.save(PersonHobbyTable.builder()
                .hobbyId(hobby.getId())
                .personId(person.getId())
                .build());
        return listHobbies(person.getId());
    }

    @Override
    public Person updatePersonAddress(Person person, Address address) {
        assert address.getId() != null;
        assert person.getId() != null;
        Optional<PersonTable> result = personRepository.findById(person.getId());
        assert result.isPresent();
        PersonTable toUpdate = result.get();
        toUpdate.setAddressId(address.getId());
        PersonTable updated = personRepository.save(toUpdate);
        return map(updated);
    }


    @Override
    public PersonHobbies listHobbies(Integer personId) {
        assert personId != null;
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        Optional<PersonTable> result = personRepository.findById(personId);
        assert result.isPresent();
        List<PersonHobbyTable> hobbyRows = personHobbyRepository.findByPersonId(personId);
        List<Hobby>  hobbies = Lists.newArrayList();
        for (PersonHobbyTable hobbyRow : hobbyRows) {
            Optional<HobbyTable> hobby = hobbyRepository.findById(hobbyRow.getHobbyId());
            assert hobby.isPresent();
            hobbies.add(mapper.map(hobby.get()));
        }
        return PersonHobbies.builder()
                .hobbies(hobbies)
                .person(map(result.get()))
                .build();
    }

    @Override
    public PersonAddress listAddress(Integer personId) {
        assert personId != null;
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        Optional<PersonTable> result = personRepository.findById(personId);
        assert result.isPresent();
        PersonTable person = result.get();
        PersonAddress.PersonAddressBuilder builder = PersonAddress.builder();
        builder.person(map(person));
        if (person.getAddressId() != null) {
            Optional<AddressTable> addressTable = addressRepository.findById(person.getAddressId());
            assert addressTable.isPresent();
            builder.address(mapper.map(addressTable.get()));
        }
        return builder.build();
    }

    @Override
    public List<Hobby> listHobbies() {
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        Iterable<HobbyTable> result = hobbyRepository.findAll();
        List<Hobby> allHobbies = Lists.newArrayList();
        result.forEach(hobbyTable -> allHobbies.add(mapper.map(hobbyTable)));
        return allHobbies;
    }

    @Override
    public List<Gender> listGenders() {
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        Iterable<GenderTable> result = genderRepository.findAll();
        List<Gender> resultList = Lists.newArrayList();
        result.forEach(row -> resultList.add(mapper.map(row)));
        return resultList;
    }

    @Override
    public List<Address> listAddresses() {
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        Iterable<AddressTable> result = addressRepository.findAll();
        List<Address> resultList = Lists.newArrayList();
        result.forEach(row -> resultList.add(mapper.map(row)));
        return resultList;
    }

    private Address save(Address address) {
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        AddressTable target = mapper.map(address);
        AddressTable result = addressRepository.save(target);
        return mapper.map(result);
    }

    private Gender save(Gender gender) {
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        GenderTable target = mapper.map(gender);
        GenderTable result = genderRepository.save(target);
        return mapper.map(result);
    }

    private Person save(Person person) {
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        PersonTable target = mapper.map(person);
        target.setGenderId(person.getGender().getId());
        PersonTable personTable = personRepository.save(target);
        return map(personTable);
    }

    private Hobby save(Hobby hobby) {
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        HobbyTable target = mapper.map(hobby);
        HobbyTable hobbyTable = hobbyRepository.save(target);
        return mapper.map(hobbyTable);
    }

    private Person map(PersonTable updated) {
        PersonManagerMapper mapper =  PersonManagerMapper.INSTANCE;
        Optional<GenderTable> gender = genderRepository.findById(updated.getGenderId());
        assert gender.isPresent();
        if (updated.getAddressId() == null) {
            return mapper.map(updated,gender.get(),null);
        }
        Optional<AddressTable> addressTable = addressRepository.findById(updated.getAddressId());
        assert addressTable.isPresent();
        return mapper.map(updated, gender.get(),addressTable.get());
    }



}
