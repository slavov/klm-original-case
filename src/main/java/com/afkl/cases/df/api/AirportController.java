package com.afkl.cases.df.api;

import com.afkl.cases.df.helper.Sort;
import com.afkl.cases.df.model.dto.AirportsResponse;
import com.afkl.cases.df.model.dto.Location;
import com.afkl.cases.df.travel.TravelApiClient;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/airports", produces = MediaType.APPLICATION_JSON_VALUE)
public class AirportController {

    private TravelApiClient travelApiClient;

    @GetMapping
    public AirportsResponse getAirports(@RequestParam(value = "size", required = false) final Integer size,
                                        @RequestParam(value = "page", required = false) final Integer page,
                                        @RequestParam(value = "term", required = false) final String term,
                                        @RequestParam(value = "sort", required = false, defaultValue = "desc") final Sort sort) {
        return travelApiClient.fetchAirports(size, page, term, sort);
    }

    @GetMapping(value = "/{code}")
    public Location getAirport(@PathVariable final String code) {
        //FIX: with param lang=nl & lang=en the data is the same
        return travelApiClient.fetchAirport(code);
    }
}
