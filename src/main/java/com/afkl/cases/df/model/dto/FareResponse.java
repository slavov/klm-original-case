package com.afkl.cases.df.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = FareResponse.FareResponseBuilder.class)
public class FareResponse {

    private final double amount;
    private final Currency currency;
    private final String origin;
    private final String destination;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class FareResponseBuilder {
    }

}
