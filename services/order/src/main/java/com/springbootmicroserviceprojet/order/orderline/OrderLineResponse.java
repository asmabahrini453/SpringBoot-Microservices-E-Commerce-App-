package com.springbootmicroserviceprojet.order.orderline;

public record OrderLineResponse(
        Integer id,
        double quantity
) { }