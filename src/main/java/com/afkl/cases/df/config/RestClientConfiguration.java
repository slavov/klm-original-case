package com.afkl.cases.df.config;

import com.afkl.cases.df.travel.RestTemplateAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestClientConfiguration {

    @Bean
    public RestTemplate restTemplate(final RestTemplateAuthenticationInterceptor authenticationInterceptor) {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(authenticationInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
