package com.afkl.cases.df.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = Embedded.EmbeddedBuilder.class)
public class Embedded {

    private final List<Location> locations;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class EmbeddedBuilder {
    }
}
