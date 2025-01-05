package com.springbootmicroserviceprojet.order.customer;

import com.springbootmicroserviceprojet.order.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}",
        configuration = FeignClientConfig.class
)
public interface CustomerClient {
    @GetMapping("/{customerId}")
    Optional<CustomerResponse> findCustomerById(@PathVariable("customerId") String customerId);
}