package com.springbootmicroserviceprojet.ecommerce.kafka.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
     Integer productId;
     String name;
     String description;
     BigDecimal price;
     double quantity;
}
