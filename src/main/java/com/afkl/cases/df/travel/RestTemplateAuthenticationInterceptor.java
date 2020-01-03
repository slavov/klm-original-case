package com.afkl.cases.df.travel;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@AllArgsConstructor
public class RestTemplateAuthenticationInterceptor implements ClientHttpRequestInterceptor {

    private TokenProvider tokenProvider;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            request.getHeaders().put(HttpHeaders.AUTHORIZATION,
                    Collections.singletonList("Bearer " + tokenProvider.requestToken()));
        }
        return execution.execute(request, body);
    }
}
