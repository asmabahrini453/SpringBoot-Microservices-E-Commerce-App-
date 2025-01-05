package com.springbootmicroserviceprojet.ecommerce.kafka.order;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class Customer {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
}
