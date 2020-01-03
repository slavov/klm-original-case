package com.afkl.cases.df.travel;

import com.afkl.cases.df.model.dto.OauthTokenResponse;
import com.afkl.cases.df.service.OauthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenProvider {

    private final String clientId;
    private final String secret;
    private OauthTokenResponse oauthTokenResponse;
    private Instant tokenRequestedAt;
    private OauthService oauthService;


    public TokenProvider(final OauthService oauthService,
                         @Value("${client-id}") final String clientId,
                         @Value("${secret}") final String secret) {
        this.oauthService = oauthService;
        this.clientId = clientId;
        this.secret = secret;
    }

    public String requestToken() {
        if (oauthTokenResponse == null || isTokenExpired()) {
            oauthTokenResponse = oauthService.requestOauthToken(clientId, secret);
            tokenRequestedAt = Instant.now();
        }
        return oauthTokenResponse.getAccessToken();
    }

    private boolean isTokenExpired() {
        final Instant tokenExpiredAt = tokenRequestedAt.plus(oauthTokenResponse.getExpiresIn(), ChronoUnit.SECONDS);
        return Instant.now().compareTo(tokenExpiredAt) > 0;
    }
}
