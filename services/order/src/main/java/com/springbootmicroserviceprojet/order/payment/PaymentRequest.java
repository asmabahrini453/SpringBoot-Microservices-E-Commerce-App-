package com.springbootmicroserviceprojet.order.payment;

import com.springbootmicroserviceprojet.order.customer.CustomerResponse;
import com.springbootmicroserviceprojet.order.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}