package com.apgsga.testing.sample.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = PersonAddress.PersonAddressBuilder.class)
@Value
@Builder
public class PersonAddress {

    Person person;
    Address address;
    @JsonPOJOBuilder(withPrefix = "")
    public static class PersonAddressBuilder {}
}
