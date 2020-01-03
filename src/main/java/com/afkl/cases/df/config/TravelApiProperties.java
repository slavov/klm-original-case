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

    @Value("${url.token}")
    private String token;
    @URL
    @Value("${url.airports}")
    private String airports;

    @SneakyThrows
    public URI getAirports() {
        return new URI(airports);
    }

    public void setAirports(final String airports) {
        this.airports = airports;
    }

}
