package com.afkl.cases.df.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = AirportsResponse.AirportsResponseBuilder.class)
public class AirportsResponse {

    @NotNull
    private final PageInfo page;

    @JsonProperty("_embedded")
    private final Embedded embedded;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class AirportsResponseBuilder {
    }
}
