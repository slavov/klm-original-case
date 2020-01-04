package com.afkl.cases.df.api;

import com.afkl.cases.df.model.dto.Fare;
import com.afkl.cases.df.service.FareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/fares", produces = MediaType.APPLICATION_JSON_VALUE)
public class FareController {

    private FareService fareService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(value = "/{origin}/{destination}")
    public Fare fetchFare(@PathVariable final String origin, @PathVariable final String destination) {
        Fare.FareBuilder fareBuilder = Fare.builder();
        final var originLocation = fareService.fetchAirportLocation(origin).thenAcceptAsync(fareBuilder::origin);
        final var destinationLocation = fareService.fetchAirportLocation(destination).thenAcceptAsync(fareBuilder::destination);
        final var fare = fareService.fetchFare(origin, destination).thenAcceptAsync(f -> {
            fareBuilder.amount(f.getAmount());
            fareBuilder.currency(f.getCurrency());
        });
        CompletableFuture.allOf(originLocation, destinationLocation, fare).join();
        return fareBuilder.build();
    }
}
