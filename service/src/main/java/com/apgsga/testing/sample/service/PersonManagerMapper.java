package com.apgsga.testing.sample.service;

import com.apgsga.testing.sample.api.Address;
import com.apgsga.testing.sample.api.Gender;
import com.apgsga.testing.sample.api.Hobby;
import com.apgsga.testing.sample.api.Person;
import com.apgsga.testing.sample.db.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface PersonManagerMapper {

    PersonManagerMapper INSTANCE = Mappers.getMapper(PersonManagerMapper.class);
    AddressTable map(Address address);
    Address map(AddressTable addressTable);
    GenderTable map(Gender gender);
    Gender map(GenderTable genderTable);
    @Mapping(source="gender.id", target="genderId")
    @Mapping(source="address.id", target="addressId")
    PersonTable map(Person person);
    @Mapping(source="genderTable", target="gender")
    @Mapping(source="addressTable", target="address")
    @Mapping(source="personTable.id", target="id")
    @Mapping(source="personTable.firstName", target="firstName")
    @Mapping(source="personTable.secondName", target="secondName")
    @Mapping(source="personTable.birthDay", target="birthDay")
    Person map(PersonTable personTable, GenderTable genderTable, AddressTable addressTable);
    HobbyTable map(Hobby hobby);
    Hobby map(HobbyTable hobbyTable);

}
