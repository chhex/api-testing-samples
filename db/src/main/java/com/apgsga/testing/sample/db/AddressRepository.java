package com.apgsga.testing.sample.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<AddressTable, Integer> {
    List<AddressTable> findByStreetName(String streetName);
    AddressTable findByStreetNameAndStreetNumber(String streetName, String streetNumber);
    List<AddressTable> findByPostalCode(String postalCode);
}
