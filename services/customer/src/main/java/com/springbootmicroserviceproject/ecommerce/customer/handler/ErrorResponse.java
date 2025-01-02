package com.springbootmicroserviceproject.ecommerce.customer.handler;

import java.util.Map;
import java.util.Set;

public record ErrorResponse(
        Map<String, String> errors
) {

}