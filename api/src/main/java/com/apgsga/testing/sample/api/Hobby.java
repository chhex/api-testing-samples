package com.apgsga.testing.sample.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = Hobby.HobbyBuilder.class)
@Value
@Builder
public class Hobby {
    Long id;
    String shortName;
    String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class HobbyBuilder {}
}
