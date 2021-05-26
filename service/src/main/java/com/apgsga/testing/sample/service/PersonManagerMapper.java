package com.apgsga.testing.sample.service;

import com.apgsga.testing.sample.api.Address;
import com.apgsga.testing.sample.db.AddressTable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonManagerMapper {

    PersonManagerMapper INSTANCE = Mappers.getMapper(PersonManagerMapper.class);
    AddressTable map(Address address);
    Address map(AddressTable addressTable);
}
