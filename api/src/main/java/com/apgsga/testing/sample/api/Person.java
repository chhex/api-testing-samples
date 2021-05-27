package com.apgsga.testing.sample.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@JsonDeserialize(builder = Person.PersonBuilder.class)
@Value
@Builder
public class Person {

    Integer id;
    String firstName;
    String secondName;
    Date birthDay;
    Gender gender;
    Address address;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PersonBuilder {}
}
