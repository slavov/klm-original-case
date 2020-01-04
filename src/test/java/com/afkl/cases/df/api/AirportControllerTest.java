package com.afkl.cases.df.api;

import com.afkl.cases.df.helper.Sort;
import com.afkl.cases.df.model.dto.AirportsResponse;
import com.afkl.cases.df.model.dto.Location;
import com.afkl.cases.df.travel.TravelApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AirportControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private TravelApiClient travelApiClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void fetchAirportByCode() {
        //Given
        final var location = Location.builder().code("BCN").build();

        //When
        when(travelApiClient.fetchAirport(location.getCode())).thenReturn(location);
        restTemplate.getForObject("http://localhost:" + port + "travel/v1/airports/" + location.getCode(),
                Location.class);

        //Then
        verify(travelApiClient, times(1)).fetchAirport(eq(location.getCode()));
    }

    @Test
    void fetchAirports() {
        //When
        when(travelApiClient.fetchAirports(eq(1), eq(1), eq("GS"), eq(Sort.asc)))
                .thenReturn(AirportsResponse.builder().build());
        restTemplate.getForObject("http://localhost:" + port + "travel/v1/airports?size=1&page=1&term=GS&sort=asc", AirportsResponse.class);

        //Then
        verify(travelApiClient, times(1)).fetchAirports(eq(1), eq(1), eq("GS"), eq(Sort.asc));
    }

}