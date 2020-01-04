package com.afkl.cases.df.config;

import com.afkl.cases.df.filter.MetricsFilter;
import com.afkl.cases.df.service.MetricsService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<MetricsFilter> metricsFilter(final MetricsService service) {
        FilterRegistrationBean<MetricsFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new MetricsFilter(service));
        registrationBean.addUrlPatterns("/v1/*");

        return registrationBean;
    }
}
