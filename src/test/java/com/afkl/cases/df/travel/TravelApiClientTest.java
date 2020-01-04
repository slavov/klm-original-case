package com.afkl.cases.df.travel;

import com.afkl.cases.df.config.TravelApiProperties;
import com.afkl.cases.df.exception.ClientException;
import com.afkl.cases.df.exception.ServerException;
import com.afkl.cases.df.helper.Sort;
import com.afkl.cases.df.model.dto.AirportsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TravelApiClientTest {

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private TravelApiProperties travelApiProperties;

    @Autowired
    private TravelApiClient client;

    @Test
    void fetchAirportsAsc() throws URISyntaxException, IOException {
        //Given
        ResponseEntity<AirportsResponse> responseEntity = new ResponseEntity<>(getAirportResponse(), HttpStatus.OK);

        //When
        when(travelApiProperties.getAirportsUri()).thenReturn(new URI("localhost"));
        when(restTemplate.getForEntity(any(), eq(AirportsResponse.class))).thenReturn(responseEntity);
        final var result = client.fetchAirports(1, 1, "GS", Sort.asc);

        //Then
        assertEquals("YQM", result.getEmbedded().getLocations().get(0).getCode());
    }

    @Test
    void fetchAirportsDesc() throws URISyntaxException, IOException {
        //Given
        ResponseEntity<AirportsResponse> responseEntity = new ResponseEntity<>(getAirportResponse(), HttpStatus.OK);

        //When
        when(travelApiProperties.getAirportsUri()).thenReturn(new URI("localhost"));
        when(restTemplate.getForEntity(any(), eq(AirportsResponse.class))).thenReturn(responseEntity);
        final var result = client.fetchAirports(1, 1, "GS", Sort.desc);

        //Then
        assertEquals("BBA", result.getEmbedded().getLocations().get(0).getCode());
    }

    @Test
    void fetchAirportsThrowsClientException() throws URISyntaxException, IOException {
        //When
        when(travelApiProperties.getAirportsUri()).thenReturn(new URI("localhost"));
        when(restTemplate.getForEntity(any(), eq(AirportsResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        final var clientException = assertThrows(ClientException.class,
                () -> client.fetchAirports(0, 1, "GS", Sort.desc));

        //Then
        assertEquals("Travel API returned 400 for airports", clientException.getMessage());
    }

    @Test
    void fetchAirportsThrowsServerException() throws URISyntaxException, IOException {
        //When
        when(travelApiProperties.getAirportsUri()).thenReturn(new URI("localhost"));
        when(restTemplate.getForEntity(any(), eq(AirportsResponse.class))).thenThrow(RestClientException.class);
        final var serverException = assertThrows(ServerException.class,
                () -> client.fetchAirports(0, 1, "GS", Sort.desc));

        //Then
        assertEquals("Server threw an error when fetching airports", serverException.getMessage());
    }

    private AirportsResponse getAirportResponse() throws IOException, URISyntaxException {
        final var path = Paths.get(TravelApiClientTest.class.getResource("/travelApiResponse.json").toURI());
        String json = Files.readString(path);
        return new ObjectMapper().readValue(json, AirportsResponse.class);
    }
}