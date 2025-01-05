package com.springbootmicroserviceproject.ecommerce.customer;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Customer {
    @Id
    private String id;

    private String firstname;
    private String lastname;
    private String email;

    @Embedded
    private Address address;

    @PrePersist
    public void prePersist() {
        // Generate a UUID when a new Customer is created if id is not already set
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
