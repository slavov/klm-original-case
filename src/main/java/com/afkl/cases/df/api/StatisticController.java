package com.afkl.cases.df.api;

import com.afkl.cases.df.model.dto.Statistics;
import com.afkl.cases.df.service.MetricsService;
import com.afkl.cases.df.transformer.MetricsEntityToStatisticDtoTransformer;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticController {

    private MetricsService service;
    private MetricsEntityToStatisticDtoTransformer transformer;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Statistics> getStatistics() {
        return ResponseEntity.ok(transformer.apply(service.findAll()));
    }
}
