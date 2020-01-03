package com.afkl.cases.df.api;

import com.afkl.cases.df.model.dto.AirportsResponse;
import com.afkl.cases.df.travel.TravelApiClient;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/airports")
public class AirportController {

    private TravelApiClient travelApiClient;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AirportsResponse getAirports() {
        return travelApiClient.fetchAirports().get();
    }
}
