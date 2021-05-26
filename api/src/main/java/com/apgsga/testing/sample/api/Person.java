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

    Long id;
    String firstName;
    String giveName;
    Date birthDay;
    Gender gender;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PersonBuilder {}
}
