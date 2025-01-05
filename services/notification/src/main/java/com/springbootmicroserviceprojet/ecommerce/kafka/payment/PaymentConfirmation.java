package com.springbootmicroserviceprojet.ecommerce.kafka.payment;

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

     String orderReference;
     BigDecimal amount;
     PaymentMethod paymentMethod;
     String customerFirstname;
     String customerLastname;
     String customerEmail;
}