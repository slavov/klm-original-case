package com.afkl.cases.df.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = PageInfo.PageInfoBuilder.class)
public class PageInfo {

    private final int size;
    private final int totalElements;
    private final int totalPages;
    private final int number;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PageInfoBuilder {
    }

}
