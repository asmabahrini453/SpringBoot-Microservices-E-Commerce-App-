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
    @Column(insertable = false, updatable = false)
         String orderReference;
         BigDecimal totalAmount;
    @Column(insertable = false, updatable = false)
    PaymentMethod paymentMethod;

        @ManyToOne
         Customer customer;

        @OneToMany
         List<Product> products;



}
