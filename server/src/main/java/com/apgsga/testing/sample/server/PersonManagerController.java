package com.apgsga.testing.sample.server;

import com.apgsga.testing.sample.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pm")
public class PersonManagerController implements PersonManagerService {
    @Autowired
    private PersonManagerService service;

    @Override
    @DeleteMapping
    public void deleteAll() {
        service.deleteAll();
    }

    @Override
    @PostMapping(path = "/address")
    public Address create(@RequestBody Address address) {
        return service.create(address);
    }

    @Override
    public Gender create(Gender gender) {
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
    public List<Hobby> create(List<Hobby> hobbies) {
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
    @GetMapping(path = "/person/{personId}")
    public Person findById(@PathVariable Integer personId) {
        return service.findById(personId);
    }

    @Override
    public PersonHobbies addHobby(Person person, Hobby hobby) {
        return null;
    }

    @Override
    public Person updatePersonAddress(Person person, Address address) {
        return null;
    }

    @Override
    public PersonHobbies listHobbies(Integer personId) {
        return null;
    }

    @Override
    public PersonAddress listAddress(Integer personId) {
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
    @GetMapping(path ="/address")
    public List<Address> listAddresses() {
        return service.listAddresses();
    }
}
