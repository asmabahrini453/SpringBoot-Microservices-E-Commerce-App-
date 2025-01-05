package com.springbootmicroserviceprojet.ecommerce.kafka.order;

import com.springbootmicroserviceprojet.ecommerce.kafka.payment.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderConfirmation {
    @Column(name = "order_confirmation_reference")
    private String orderReference;

    private BigDecimal totalAmount;

    @Column(name = "order_payment_method")
    private PaymentMethod paymentMethod;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "customer_id")),
            @AttributeOverride(name = "firstname", column = @Column(name = "customer_firstname")),
            @AttributeOverride(name = "lastname", column = @Column(name = "customer_lastname")),
            @AttributeOverride(name = "email", column = @Column(name = "customer_email"))
    })
    private Customer customer;

    @ElementCollection
    @CollectionTable(name = "order_confirmation_products")
    private List<Product> products;
}