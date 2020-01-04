package com.afkl.cases.df.filter;

import com.afkl.cases.df.model.entity.Metrics;
import com.afkl.cases.df.service.MetricsService;
import lombok.AllArgsConstructor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@AllArgsConstructor
public class MetricsFilter extends HttpFilter {

    private MetricsService service;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        final var start = Instant.now();
        chain.doFilter(req, res);
        final var time = Duration.between(start, Instant.now()).toMillis();
        final var status = res.getStatus();
        service.save(new Metrics(null, time, status));
    }
}