package com.apgsga.testing.sample.client.rest;

import com.apgsga.testing.sample.api.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@Profile("rest")
public class RestClient implements PersonManagerService {

    @Value("${baseUrl:http://localhost:8080}")
    String baseUrl;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public void deleteAll() {
        restTemplate.delete(baseUrl + "/api/pm");
    }

    @Override
    public Address create(Address address) {
        ResponseEntity<Address> addressResponseEntity = restTemplate.postForEntity(baseUrl + "/api/pm/address", address, Address.class);
        return addressResponseEntity.getBody();
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
    public Person findById(Integer personId) {
        Map<String, Integer> params = Maps.newHashMap();
        params.put("personId", personId);
        ResponseEntity<Person> addressResponseEntity = restTemplate.getForEntity(String.format( "%s/api/pm/person/{personId}",baseUrl), Person.class,params);
        return addressResponseEntity.getBody();
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
    public List<Address> listAddresses() {
        ResponseEntity<Address[]> response = restTemplate.getForEntity(baseUrl + "/api/pm/address", Address[].class);
        return Lists.newArrayList(response.getBody());
    }
}
