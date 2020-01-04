package com.afkl.cases.df.transformer;

import com.afkl.cases.df.model.dto.Statistics;
import com.afkl.cases.df.model.entity.Metrics;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Predicate;

@Component
public class MetricsEntityToStatisticDtoTransformer {

    public Statistics apply(List<Metrics> metrics) {
        return Statistics.builder().totalNumberRequestsProcessed(metrics.size())
                .totalNumberRequest2xx(metrics.stream().filter(filterByCode(200, 226)).count())
                .totalNumberRequest4xx(metrics.stream().filter(filterByCode(400, 499)).count())
                .totalNumberRequest5xx(metrics.stream().filter(filterByCode(500, 598)).count())
                .averageResponse(calculate(metrics))
                .minResponse(metrics.stream().mapToLong(Metrics::getTime).min().orElse(0L))
                .maxResponse(metrics.stream().mapToLong(Metrics::getTime).max().orElse(0L))
                .build();
    }

    private Predicate<Metrics> filterByCode(final int codeFrom, final int codeTo) {
        return metrics -> metrics.getCode() >= codeFrom && metrics.getCode() <= codeTo;
    }

    private BigDecimal calculate(final List<Metrics> metrics) {
        return BigDecimal.valueOf(metrics.stream().mapToLong(Metrics::getTime).average().orElse(0d))
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
