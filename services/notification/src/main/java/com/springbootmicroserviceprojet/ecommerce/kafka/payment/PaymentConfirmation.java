package com.springbootmicroserviceprojet.ecommerce.kafka.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PaymentConfirmation {
     @Column(name = "payment_confirmation_reference")
     private String orderReference;

     private BigDecimal amount;

     @Column(name = "payment_confirmation_method")
     private PaymentMethod paymentMethod;

     @Column(name = "payment_customer_firstname")
     private String customerFirstname;

     @Column(name = "payment_customer_lastname")
     private String customerLastname;

     @Column(name = "payment_customer_email")
     private String customerEmail;
}