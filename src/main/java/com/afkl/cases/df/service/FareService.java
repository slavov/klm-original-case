package com.afkl.cases.df.service;

import com.afkl.cases.df.model.dto.FareResponse;
import com.afkl.cases.df.model.dto.Location;

import java.util.concurrent.CompletableFuture;

public interface FareService {

    CompletableFuture<Location> fetchAirportLocation(final String location);

    CompletableFuture<FareResponse> fetchFare(final String origin, final String destination);

}
