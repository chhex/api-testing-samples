package com.apgsga.testing.sample.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = Gender.GenderBuilder.class)
@Value
@Builder
public class Gender {
    Long id;
    String designation;
    String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GenderBuilder {}
}
