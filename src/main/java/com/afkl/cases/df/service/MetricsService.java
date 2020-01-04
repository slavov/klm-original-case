package com.afkl.cases.df.service;

import com.afkl.cases.df.model.entity.Metrics;

import java.util.List;

public interface MetricsService {

    Metrics save(Metrics metrics);

    List<Metrics> findAll();

}
