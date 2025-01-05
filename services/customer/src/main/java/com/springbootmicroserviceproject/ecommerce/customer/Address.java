package com.springbootmicroserviceproject.ecommerce.customer;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
}
