package com.afkl.cases.df.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = Statistics.StatisticsBuilder.class)
public class Statistics {

    private final int totalNumberRequestsProcessed;
    private final long totalNumberRequest2xx;
    private final long totalNumberRequest4xx;
    private final long totalNumberRequest5xx;
    private final BigDecimal averageResponse;
    private final long minResponse;
    private final long maxResponse;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class StatisticsBuilder {
    }
}