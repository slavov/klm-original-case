package com.afkl.cases.df.travel;

import com.afkl.cases.df.config.TravelApiProperties;
import com.afkl.cases.df.exception.ServerException;
import com.afkl.cases.df.model.dto.AirportsResponse;
import lombok.AllArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TravelApiClient {

    private RestTemplate restTemplate;
    private TravelApiProperties travelApiProperties;

    public Optional<AirportsResponse> fetchAirports() {
        try {
            final URIBuilder uriBuilder = new URIBuilder(travelApiProperties.getAirports());
            final AirportsResponse body = restTemplate.getForEntity(uriBuilder.build(), AirportsResponse.class).getBody();
            return Optional.ofNullable(body.toBuilder().embedded(body.getEmbedded()).build());
        } catch (URISyntaxException e) {
            throw new ServerException("Airport URI could not be parsed as a URI reference.", e);
        }
    }
}
