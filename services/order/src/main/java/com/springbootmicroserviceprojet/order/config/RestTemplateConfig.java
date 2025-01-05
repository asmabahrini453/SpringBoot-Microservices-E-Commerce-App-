package com.springbootmicroserviceprojet.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();

        restTemplate.getInterceptors().add(
                (request, body, execution) -> {
                    ServletRequestAttributes attributes =
                            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    if (attributes != null) {
                        String token = attributes.getRequest()
                                .getHeader(HttpHeaders.AUTHORIZATION);
                        if (token != null) {
                            request.getHeaders()
                                    .set(HttpHeaders.AUTHORIZATION, token);
                        }
                    }
                    return execution.execute(request, body);
                }
        );

        return restTemplate;
    }
}