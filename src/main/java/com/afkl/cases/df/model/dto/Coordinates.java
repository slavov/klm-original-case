package com.afkl.cases.df.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = Coordinates.CoordinatesBuilder.class)
public final class Coordinates {

    private final double latitude;
    private final double longitude;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class CoordinatesBuilder {
    }

}
