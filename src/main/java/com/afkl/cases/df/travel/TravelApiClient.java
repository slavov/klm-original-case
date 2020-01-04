package com.afkl.cases.df.travel;

import com.afkl.cases.df.config.TravelApiProperties;
import com.afkl.cases.df.exception.ClientException;
import com.afkl.cases.df.exception.ServerException;
import com.afkl.cases.df.helper.Sort;
import com.afkl.cases.df.model.dto.AirportsResponse;
import com.afkl.cases.df.model.dto.Embedded;
import com.afkl.cases.df.model.dto.FareResponse;
import com.afkl.cases.df.model.dto.Location;
import lombok.AllArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TravelApiClient {

    private RestTemplate restTemplate;
    private TravelApiProperties travelApiProperties;

    public AirportsResponse fetchAirports(final Integer size, final Integer page, final String term, final Sort sort) {
        try {
            final var uriBuilder = buildUri(size, page, term);
            final var body = restTemplate.getForEntity(uriBuilder.build(), AirportsResponse.class).getBody();
            return body.toBuilder()
                    .embedded(sortByCode(body.getEmbedded(), sort))
                    .build();
        } catch (URISyntaxException e) {
            throw new ServerException("Airport URI could not be parsed as a URI reference.", e);
        } catch (final HttpClientErrorException e) {
            final var message = String.format("Travel API returned %s for airports", e.getRawStatusCode());
            throw new ClientException(message, e);
        } catch (final RestClientException e) {
            throw new ServerException("Server threw an error when fetching airports", e);
        }
    }

    public Location fetchAirport(final String code) {
        final ResponseEntity<Location> response;
        try {
            response = restTemplate.getForEntity(travelApiProperties.getAirportsUri(code), Location.class);
        } catch (final HttpClientErrorException e) {
            final var message = String.format("Travel API returned %s for airport code: %s", e.getRawStatusCode(), code);
            throw new ClientException(message, e);
        } catch (final RestClientException e) {
            throw new ServerException("Server threw an error when fetching airport by code", e);
        }
        return response.getBody();
    }

    public FareResponse fetchFare(final String origin, final String destination) {
        final ResponseEntity<FareResponse> response;
        try {
            response = restTemplate.getForEntity(travelApiProperties.getFareUri(origin, destination), FareResponse.class);
        } catch (final HttpClientErrorException e) {
            final var message = String.format("Travel API returned %s for fare from %s, to %s",
                    e.getRawStatusCode(), origin, destination);
            throw new ClientException(message, e);
        } catch (final RestClientException e) {
            throw new ServerException("Server threw an error by fetching fare", e);
        }
        return response.getBody();
    }

    private URIBuilder buildUri(final Integer size, final Integer page, final String term) {
        final var uriBuilder = new URIBuilder(travelApiProperties.getAirportsUri());
        Optional.ofNullable(size).ifPresent(s -> uriBuilder.setParameter("size", String.valueOf(s)));
        Optional.ofNullable(page).ifPresent(p -> uriBuilder.setParameter("page", String.valueOf(p)));
        Optional.ofNullable(term).ifPresent(t -> uriBuilder.setParameter("term", t));
        return uriBuilder;
    }

    private Embedded sortByCode(final Embedded embedded, final Sort sort) {
        final var locationComparator =
                sort.equals(Sort.desc) ?
                        Comparator.comparing(Location::getCode)
                        : Collections.reverseOrder(Comparator.comparing(Location::getCode));

        return embedded.toBuilder()
                .locations(embedded.getLocations()
                        .stream()
                        .sorted(locationComparator)
                        .collect(Collectors.toList()))
                .build();
    }
}
