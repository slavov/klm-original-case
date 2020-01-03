package com.afkl.cases.df.travel;

import com.afkl.cases.df.config.TravelApiProperties;
import com.afkl.cases.df.exception.ServerException;
import com.afkl.cases.df.helper.Sort;
import com.afkl.cases.df.model.dto.AirportsResponse;
import com.afkl.cases.df.model.dto.Embedded;
import com.afkl.cases.df.model.dto.Location;
import lombok.AllArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;
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

    public Optional<AirportsResponse> fetchAirports(final Integer size, final Integer page, final String term, Sort sort) {
        try {
            final var uriBuilder = buildUri(size, page, term);
            final var body = restTemplate.getForEntity(uriBuilder.build(), AirportsResponse.class).getBody();
            return Optional.ofNullable(body.toBuilder()
                    .embedded(sortByCode(body.getEmbedded(), sort))
                    .build());
        } catch (URISyntaxException e) {
            throw new ServerException("Airport URI could not be parsed as a URI reference.", e);
        }
    }

    private URIBuilder buildUri(final Integer size, final Integer page, final String term) {
        final var uriBuilder = new URIBuilder(travelApiProperties.getAirports());
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
