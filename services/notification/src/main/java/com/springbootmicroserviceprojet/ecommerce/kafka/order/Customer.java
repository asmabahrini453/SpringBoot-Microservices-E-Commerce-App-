package com.springbootmicroserviceprojet.ecommerce.kafka.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Customer {
    @Id
     String id;
     String firstname;
     String lastname;
     String email;
}
