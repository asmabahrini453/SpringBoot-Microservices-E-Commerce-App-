package com.springbootmicroserviceprojet.order.kafka;

import com.springbootmicroserviceprojet.order.customer.CustomerResponse;
import com.springbootmicroserviceprojet.order.order.PaymentMethod;
import com.springbootmicroserviceprojet.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}