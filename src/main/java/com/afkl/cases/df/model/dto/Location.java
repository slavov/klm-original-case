package com.afkl.cases.df.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@AllArgsConstructor
@JsonInclude(NON_NULL)
@Builder(toBuilder = true)
@JsonDeserialize(builder = Location.LocationBuilder.class)
public class Location {

    private final String code;
    private final String name;
    private final String description;
    private final Coordinates coordinates;
    private final Location parent;
    private final Set<Location> children;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class LocationBuilder {
    }
}
