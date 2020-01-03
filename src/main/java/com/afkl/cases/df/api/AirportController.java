package com.afkl.cases.df.api;

import com.afkl.cases.df.model.dto.AirportsResponse;
import com.afkl.cases.df.travel.TravelApiClient;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/airports")
public class AirportController {

    private TravelApiClient travelApiClient;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AirportsResponse getAirports(@RequestParam(value = "size", required = false, defaultValue = "1") final Integer size,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
                                        @RequestParam(value = "term", required = false) final String term) {
        return travelApiClient.fetchAirports(size, page, term).get();
    }
}
