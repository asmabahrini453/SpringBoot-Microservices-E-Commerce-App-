package com.springbootmicroserviceprojet.ecommerce.kafka.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class Product {
 @Column(name = "product_id")
 private Integer productId;

 private String name;
 private String description;
 private BigDecimal price;
 private double quantity;
}