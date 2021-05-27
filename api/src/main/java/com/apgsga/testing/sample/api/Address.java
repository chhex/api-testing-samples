package com.apgsga.testing.sample.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = Address.AddressBuilder.class)
@Value
@Builder
public class Address {

    Integer id;
    String streetName;
    String streetNumber;
    String postalCode;

    @JsonPOJOBuilder(withPrefix = "")
    public static class AddressBuilder {}
}
