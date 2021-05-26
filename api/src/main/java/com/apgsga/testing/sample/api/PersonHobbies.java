package com.apgsga.testing.sample.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@JsonDeserialize(builder = PersonHobbies.PersonHobbiesBuilder.class)
@Value
@Builder
public class PersonHobbies {

    Person person;
    @Builder.Default
    List<Hobby> hobbies = Lists.newArrayList();

    @JsonPOJOBuilder(withPrefix = "")
    public static class PersonHobbiesBuilder {}
}
