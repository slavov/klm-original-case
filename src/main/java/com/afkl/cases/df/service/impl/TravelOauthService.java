package com.afkl.cases.df.service.impl;

import com.afkl.cases.df.config.TravelApiProperties;
import com.afkl.cases.df.model.dto.OauthTokenResponse;
import com.afkl.cases.df.service.OauthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@AllArgsConstructor
public class TravelOauthService implements OauthService {

    private TravelApiProperties travelApiProperties;

    @Override
    public OauthTokenResponse requestOauthToken(final String clientId, final String secret) {
        final String url = travelApiProperties.getToken();
        return new RestTemplate().exchange(url, HttpMethod.POST,
                buildHttpEntity(clientId, secret), OauthTokenResponse.class).getBody();
    }

    private HttpEntity buildHttpEntity(final String clientId, final String secret) {
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        return new HttpEntity<>(body, createHeaders(clientId, secret));
    }

    private HttpHeaders createHeaders(final String clientId, final String secret) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        String auth = clientId + ":" + secret;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        httpHeaders.set(HttpHeaders.AUTHORIZATION, authHeader);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return httpHeaders;
    }
}
