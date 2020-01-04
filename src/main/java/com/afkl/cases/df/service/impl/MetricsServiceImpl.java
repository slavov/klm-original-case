package com.afkl.cases.df.service.impl;

import com.afkl.cases.df.model.entity.Metrics;
import com.afkl.cases.df.repositories.MetricsRepository;
import com.afkl.cases.df.service.MetricsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MetricsServiceImpl implements MetricsService {

    private final MetricsRepository metricsRepository;

    @Override
    public Metrics save(Metrics metrics) {
        return metricsRepository.save(metrics);
    }

    @Override
    public List<Metrics> findAll() {
        return metricsRepository.findAll();
    }
}
