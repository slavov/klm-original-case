package com.afkl.cases.df.config;

import lombok.Data;
import lombok.SneakyThrows;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.URI;

@Data
@Configuration
@PropertySource("classpath:travel-api.properties")
public class TravelApiProperties {

    private static final String PATH_DELIMITER = "/";

    @Value("${url.token}")
    private String token;

    @URL
    @Value("${url.airports}")
    private String airports;

    @URL
    @Value("${url.fare}")
    private String fare;


    @SneakyThrows
    public URI getAirportsUri() {
        return new URI(airports);
    }

    @SneakyThrows
    public URI getAirportsUri(final String code) {
        return new URI(airports + PATH_DELIMITER + code);
    }

    @SneakyThrows
    public URI getFareUri(final String origin, final String destination) {
        return new URI(fare + PATH_DELIMITER + origin + PATH_DELIMITER + destination);
    }
}

