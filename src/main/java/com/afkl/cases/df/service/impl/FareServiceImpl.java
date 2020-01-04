package com.afkl.cases.df.service.impl;

import com.afkl.cases.df.model.dto.FareResponse;
import com.afkl.cases.df.model.dto.Location;
import com.afkl.cases.df.service.FareService;
import com.afkl.cases.df.travel.TravelApiClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class FareServiceImpl implements FareService {

    private TravelApiClient travelApiClient;

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Location> fetchAirportLocation(final String location) {
        log.info("Requesting airport location for {}", location);
        return CompletableFuture.supplyAsync(() -> travelApiClient.fetchAirport(location));
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<FareResponse> fetchFare(final String origin, final String destination) {
        log.info("Requesting fare for {}-{}", origin, destination);
        return CompletableFuture.supplyAsync(() -> travelApiClient.fetchFare(origin, destination));
    }
}
