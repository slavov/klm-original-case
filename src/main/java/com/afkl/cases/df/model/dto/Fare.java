package com.afkl.cases.df.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = Fare.FareBuilder.class)
public class Fare {

    private double amount;
    private Currency currency;
    private Location origin;
    private Location destination;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class FareBuilder {
    }

}
